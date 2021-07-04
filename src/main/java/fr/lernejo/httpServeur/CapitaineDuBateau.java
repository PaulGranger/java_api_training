package fr.lernejo.httpServeur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class CapitaineDuBateau {
    private final String id = UUID.randomUUID().toString();
    private final int[][] sea = new int[10][10];
    private final int[][] ennemySea = new int[10][10];
    private final ArrayList<String> ennemyId = new ArrayList<String>(1);
    private final ConvertCell convertCell = new ConvertCell();

    public CapitaineDuBateau(){
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                this.sea[i][j]=0;
                this.ennemySea[i][j]=0;
            }
        }
        this.sea[1][1] = 1;
    }

    public int[] isTouched(int[] cell){
        System.out.println(Arrays.toString(cell));
        int[] stringArray = new int[2];
        if (this.sea[cell[0]][cell[1]] != 9 && this.sea[cell[0]][cell[1]] != 0)
        {
            stringArray[0] = isSinked(this.sea[cell[0]][cell[1]]);
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
        if (ennemySea[i][j] != 0 || ennemySea[i][j] != 9) { return this.getConvertCell().convertSeaPositionIntoString(i, j); }
        else { return chooseCell(); }
    }

    public int stillAlive()
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (this.sea[i][j] != 0 && this.sea[i][j] != 9) { return 1; }
            }
        }
        return 0;
    }

    public int isSinked(int cellValue)
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (this.sea[i][j] == cellValue) { return 0; }
            }
        }
        return 1;
    }

    public void checkEnnemyMap(int[] cell) { this.ennemySea[cell[0]][cell[1]] = 9; }
    public String getId() { return id; }
    public ConvertCell getConvertCell() { return convertCell; }
}
