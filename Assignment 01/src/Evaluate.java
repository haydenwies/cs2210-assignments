public class Evaluate {
    
    private int tilesToWin;
    private int maxLevels;
    private char[][] gameBoard;

    public Evaluate (int size, int tilesToWin, int maxLevels) {

        this.tilesToWin = tilesToWin;
        this.maxLevels = maxLevels;

        // Populate game board with all 'e' characters
        this.gameBoard = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j=0; j < size; j++) {
                this.gameBoard[i][j] = 'e';
            }
        }

    }

    public Dictionary createDictionary() {

        return new Dictionary(9151);

    }

    public Record repeatedState(Dictionary dict) {

        String key = "";

        // Loop through 2D array and create string
        for (int i=0; i < gameBoard.length; i++) {
            for (int j=0; j < gameBoard[i].length; j++) {
                key += gameBoard[i][j]; 
            }
        }

        Record record = dict.get(key);
        
        // Variable record will contain the record if it exists and contain null if it doesn't exist
        return record;

    }

    public void insertState(Dictionary dict, int score, int level) {

        String key = "";

        // Loop through 2D array and create string
        for (int i=0; i < gameBoard.length; i++) {
            for (int j=0; j < gameBoard[i].length; j++) {
                key += gameBoard[i][j]; 
            }
        }

        Record record = new Record(key, score, level);

        // Try adding record to dict, print error if key already exists
        try {
            dict.put(record);
        } catch (DuplicatedKeyException e) {
            System.out.println(e);
        }
        

    }

    public void storePlay(int row, int col, char symbol) {

        // Saves symbol in the gameBoard with the provided row and column values
        this.gameBoard[row][col] = symbol;

    }

    public boolean squareIsEmpty(int row, int col) {

        // Checks if given gameBoard element has value 'e'
        if (this.gameBoard[row][col] == 'e') {
            return true;
        } else {
            return false;
        }

    }

    public boolean tileOfComputer(int row, int col) {

        // Checks if given gameBoard element has value 'c'
        if (this.gameBoard[row][col] == 'c') {
            return true;
        } else {
            return false;
        }

    }

    public boolean tileOfHuman(int row, int col) {

        // Checks if given gameBoard element has value 'h'
        if (this.gameBoard[row][col] == 'h') {
            return true;
        } else {
            return false;
        }

    }

    public boolean wins(char symbol) {

        // Uses private methods to check if the given symbol wins horizontally, vertically, or diagonally
        if (this.checkVertical(symbol) || this.checkHorizontal(symbol) || this.checkDiagonal(symbol)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isDraw() {

        boolean isDraw = true;

        // Loop through columns and rows to check if any 'e' characters exist
        for (int i=0; i < this.gameBoard.length; i++) {
            for (int j=0; j < this.gameBoard.length; j++) {
                if (this.gameBoard[i][j] == 'e') {
                    isDraw = false;
                }
            }
        }

        return isDraw;

    }

    public int evalBoard() {
        
        // Checks for different winning conditions and returns the proper integer value
        if (this.wins('c')) {
            return 3;
        } else if (this.wins('h')) {
            return 0;
        } else if (this.isDraw()) {
            return 2;
        } else {
            return 1;
        }

    }

    // ---------- ⬇ Private fucntions ⬇ ----------

    private boolean checkVertical(char symbol) {

        int streak = 0;

        // Loop through colums
        for (int i=0; i < this.gameBoard.length; i++) {
            // Loop through rows
            for (int j=0; j < this.gameBoard.length; j++) {

                // Increment streak if value matches symbol
                if (this.gameBoard[i][j] == symbol) {
                    streak++;
                } else {
                    streak = 0;
                }

                // If streak length is met return true
                if (streak == this.tilesToWin) {
                    return true;
                } 

            }

            streak = 0;

        }

        return false;

    }

    private boolean checkHorizontal(char symbol) {

        int streak = 0;

        // Loop through colums
        for (int i=0; i < this.gameBoard.length; i++) {
            // Loop through rows
            for (int j=0; j < this.gameBoard.length; j++) {

                // Transpose matrix
                // Increment streak if value matches symbol
                if (this.gameBoard[j][i] == symbol) {
                    streak++;
                } else {
                    streak = 0;
                }

                // If streak length is met return true
                if (streak == this.tilesToWin) {
                    return true;
                } 

            }

            streak = 0;

        }

        return false;

    }

    private boolean checkDiagonal(char symbol) {


        // Check from top left to bottom right
        // Loop through columns
        for (int i = (this.tilesToWin - this.gameBoard.length); i <= (this.gameBoard.length - this.tilesToWin); i++) {

            int streak = 0;

            // Loop through rows
            for (int j = 0; j < this.gameBoard.length; j++) {

                // Check if index exists on gameBoard
                if (0 <= i+j && i+j < this.gameBoard.length) {

                    // Increment streak if the value matches symbol
                    if (this.gameBoard[i+j][j] == symbol) {
                        streak++;
                    } else{
                        streak = 0;
                    }

                    // Return true if streak length is met
                    if (streak == this.tilesToWin) {
                        return true;
                    }

                }

            }
            
        }   

        // Check from bottom left to top right
        // Loop through columns
        for (int i = (this.tilesToWin - this.gameBoard.length); i <= (this.gameBoard.length - this.tilesToWin); i++) {

            int streak = 0;

            // Loop through rows
            for (int j = this.gameBoard.length - 1; j >= 0; j--) {

                // Check if index exists on gameBoard
                if (0 <= i + ((this.gameBoard.length-1) - j) && i + ((this.gameBoard.length-1) - j) < this.gameBoard.length) {

                    // Increment streak if the value matches symbol
                    if (this.gameBoard[i + ((this.gameBoard.length-1) - j)][j] == symbol) {
                        streak++;
                    } else {
                        streak = 0;
                    }

                    // Return true if streak length is met
                    if (streak == this.tilesToWin) {
                        return true;
                    }

                }

            }

        }
        
        return false;
           
    }

}
