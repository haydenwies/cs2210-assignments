public class Dictionary implements DictionaryADT {
    
    private int numRecords = 0;
    private Pointer[] table;
    
    public Dictionary(int size) {

        this.table = new Pointer[size];

        // Loop through all items in table and fill with empty pointers
        // These will be used to direct to proper nodes once filled
        for (int i = 0; i < table.length; i++) {
            this.table[i] = new Pointer();
        }

    }

    public int put(Record rec) throws DuplicatedKeyException {
        
        // Increment number of records
        this.numRecords++;

        // Placeholder for collision indicator
        int collision = 0;

        // Get index from hash and locate in table
        int index = this.hash(rec.getKey());        
        Node node = this.table[index].getValue();

        // Mark collision if one or more elements exists
        if (node != null) {
            collision = 1;

            // Check if key exists in table
            while (node.getNext() != null) {
                String key = node.getElement().getKey();
                if (rec.getKey().equals(key)) {
                    // If yes throw DuplicateKeyException
                    throw new DuplicatedKeyException(key);
                } else {
                    node = node.getNext();
                }
            }
            // Check key of last node, if it matches throw an exception otherwise append to end
            String key = node.getElement().getKey();
            if (rec.getKey().equals(key)) {
                throw new DuplicatedKeyException(key);
            } else {
                node.setNext(new Node(rec));
            }

        } else {

            // If no collision occurs set table pointer to new node
            table[index].setValue(new Node(rec));

        }

        return collision;

    }

    public void remove(String key) throws InexistentKeyException {

        this.numRecords--;

        // Get index from hash and locate in table
        int index = this.hash(key);
        Node node = this.table[index].getValue();
        Node prev = null;

        try {
            // Loop through node chain (if it exists) and stop once correct element is reached
            while (!node.getElement().getKey().equals(key)) {
                prev = node;
                node = node.getNext();
            }

            if (prev != null) {
                // Link pointer from previous node to the next node
                prev.setNext(node.getNext());
                // Empty node to be removed
                node = null;
            } 
        } catch (NullPointerException e) {
            throw new InexistentKeyException(key);
        }

    }

    public Record get(String key) {

        // Get index from hash and locate in table
        int index = this.hash(key);
        Node node = this.table[index].getValue();

        // Loop through node chain (if it exists) and stop once correct element is reached
        try {
            while (!node.getElement().getKey().equals(key)) {
                node = node.getNext();
            }
            // Return correct record
            return node.getElement();
        } catch (NullPointerException e) {
            // If node is null (chain is empty or exhausted) return null
            return null;
        }

    }

    public int numRecords() {

        return numRecords;

    }

    private int hash(String key) {

        int hash = (int)key.charAt(0);

        // Do calculations
        for (int i=0; i < key.length(); i++) {
            hash += ((hash + 181) * 13 + (int)(key.charAt(i))) % 9029;
        }

        // Mod table by length to ensure value is within range of table size
        return hash % this.table.length;

    }

}
