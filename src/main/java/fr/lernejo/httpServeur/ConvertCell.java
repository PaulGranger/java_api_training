package fr.lernejo.httpServeur;

public class ConvertCell {
    public int[] convertCellIntoSeaPosition(String cell){
        int[] result = new int[2];
        char[] cellChar = cell.toCharArray();
        result[0] = getIntForChar(cellChar[0]);
        if (cell.length() == 2)
            result[1] = Integer.parseInt(String.valueOf(cellChar[1])) - 1;
        else
            result[1] = 9;
        return result;
    }

    public int getIntForChar(char letter){
        return switch (letter) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            case 'E' -> 4;
            case 'F' -> 5;
            case 'G' -> 6;
            case 'H' -> 7;
            case 'I' -> 8;
            case 'J' -> 9;
            default -> -1;
        };
    }

    public String convertSeaPositionIntoString(int i, int j){
        return getCharForInt(i) + Integer.toString(j);
    }

    public char getCharForInt(int i){
        return switch (i) {
            case 0 -> 'A';
            case 1 -> 'B';
            case 2 -> 'C';
            case 3 -> 'D';
            case 4 -> 'E';
            case 5 -> 'F';
            case 6 -> 'G';
            case 7 -> 'H';
            case 8 -> 'I';
            case 9 -> 'J';
            default -> 'Z';
        };
    }
}
