package IO;
//TODO ADD THE ANSWER TO THE OPS
//TODO 128 REPETITIONS.

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;


    public MyCompressorOutputStream(OutputStream OPS) {

        out = OPS;
    }

    public void write(int b) {

    }

    private byte convertByteArr(byte[] tmp, int size) {
        int intNum = 0;
        double power = 0;
        for (int i=size; i>=0; i--){
            intNum = intNum + tmp[i] * (int)Math.pow(2,power);
            power++;
        }
        byte result = (byte)intNum;
        return result;
    }

    private byte convertByteArr(byte[] tmp) {
        int intNum = 0;
        double power = 0;
        for (int i=tmp.length-1; i>=0; i--){
            intNum = intNum + tmp[i] * (int)Math.pow(2,power);
            power++;
        }
        byte result = (byte)intNum;
        return result;
    }

    public void write(byte[] b) {
        ArrayList<Byte> temp = new ArrayList<>();//byte[] answer size is unknown
        int j = 8;//here the maze's values start
        byte[] bitSend = new byte[8];
        while (j < b.length) {
            int count=0;
            int tempcheck=0;
            while (count < 8 && j < b.length) {
                bitSend[count] = b[j];
                j++;
                count++;
            }
            if (j<=b.length)
                temp.add(convertByteArr(bitSend));
            else //when last 4 bytes are not 4 but 3\2\1
            {
                tempcheck = b.length%8;
                temp.add(convertByteArr(bitSend, tempcheck));
            }

        }


        byte[] compressedMaze = new byte[8 + temp.size()];//8 cells for maze' details and rest for 0,1 repeatitions
        int copy = 0;
        for (; copy < 8; copy++)
            compressedMaze[copy] = b[copy];//8 cells for maze's info
        while (temp.size() != 0) {//add arraylist values to the byte[] answer
            //(0,0) is start position, therefore the values on even indexes (8,10,...)represents 0 combos
            compressedMaze[copy] = (byte) (temp.remove(0).intValue());
            copy++;
        }

        try {
            for (int i = 0; i < compressedMaze.length; i++)
                out.write(compressedMaze[i]);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
