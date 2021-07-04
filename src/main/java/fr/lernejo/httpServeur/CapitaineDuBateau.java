package fr.lernejo.httpServeur;

import java.util.Random;
import java.util.UUID;

public class CapitaineDuBateau {
    private final String id = UUID.randomUUID().toString();
    private final int[][] sea = new int[10][10];
    private final int[][] ennemySea = new int[10][10];
    private final ConvertCell convertCell = new ConvertCell();
    private final int[] boatSunked = new int[10];

    public CapitaineDuBateau(){
        this.boatSunked[1] = 0;
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                this.sea[i][j] = 0;
                this.ennemySea[i][j] = 0;
            }
        }
        BateauDuCapitaine bateauDuCapitaine = new BateauDuCapitaine();
        bateauDuCapitaine.addBoat(this);
    }

    public int[] isTouched(int[] cell){
        int[] stringArray = new int[2];
        if (this.sea[cell[0]][cell[1]] != 9 && this.sea[cell[0]][cell[1]] != 0)
        {
            stringArray[0] = isSunked(this.sea[cell[0]][cell[1]], cell[0], cell[1]) == 1 ? 1 : 0;
            stringArray[1] = stillAlive();
        }
        else
        {
            stringArray[0] = 2;
            stringArray[1] = 1;
        }
        return stringArray;
    }

    public String chooseCell(){
        Random random = new Random();
        int i = random.nextInt(10);
        int j = random.nextInt(10);
        if (this.ennemySea[i][j] != 9) {
            this.ennemySea[i][j] = 9;
            return this.getConvertCell().convertSeaPositionIntoString(i, j + 1);
        }
        else { return chooseCell(); }
    }

    public int stillAlive()
    {
        if (this.boatSunked[1] != 5)
        {
            return 1;
        }
        return 0;
    }

    public int isSunked(int cellValue, int c1, int c2)
    {
        this.sea[c1][c2] = 9;
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (this.sea[i][j] == cellValue) { return 0; }
            }
        }

        this.boatSunkedPlusOne();
        return 1;
    }

    public String getId() { return id; }
    public ConvertCell getConvertCell() { return convertCell; }
    public void setSeaPosition(int i, int j, int value) { this.sea[i][j] = value; }
    public void boatSunkedPlusOne() { this.boatSunked[1] = this.boatSunked[1] + 1; }
 }
