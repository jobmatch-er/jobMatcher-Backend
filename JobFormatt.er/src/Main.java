import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        // The name of the file to open.
        String fileName = "/Users/jakobniklas/Coding/JobMatch.er/Arbeitsplätze";

        // This will reference one line at a time
        String line = null;

        try
        {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            int count = 0;

            while((line = bufferedReader.readLine()) != null)
            {
                System.out.print("<option value=\"" + count + "\">" + line + "</option>");

                count++;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex)
        {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex)
        {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }


    }
}
