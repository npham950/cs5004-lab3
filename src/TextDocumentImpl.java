/**
 * Created by nypham on 6/17/17.
 */
import javax.xml.bind.SchemaOutputResolver;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
public class TextDocumentImpl implements TextDocument {
    private String text;
    private ArrayList<String> wordArr;
    public TextDocumentImpl(String text) {
        this.text = text;
        ArrayList<String> wordArr = new ArrayList<>();
        Scanner sc = new Scanner(this.text);
        while (sc.hasNext()) {
            wordArr.add(sc.next());
        }
        this.wordArr = wordArr;
    }
    @Override
    public int getWordCount() {
        return this.wordArr.size();
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public TextDocument wrap(int columnWidth) {
        int i = 0;
        String newText = "";
        int currentLineWidth = 0;
        while (i < wordArr.size()) {
            String currWord = wordArr.get(i);
            if (currentLineWidth + currWord.length() <= columnWidth) {
                newText += currWord + " ";
                currentLineWidth += currWord.length() + 1;
                i++;
                continue;
            } else if (currWord.length() > columnWidth) {
                int numofLine = currWord.length()/columnWidth;
                int tracker = columnWidth - 1;
                for (int j = 0; j < numofLine; j++) {
                    newText += currWord.substring(currentLineWidth, tracker) + "-" + "\n";
                    currentLineWidth += tracker;
                    tracker = tracker + tracker;
                }
                newText += currWord.substring(numofLine * (columnWidth - 1) ) + " ";
                i++;
                continue;
            }
            newText += "\n";
            currentLineWidth = 0;
        }
        return new TextDocumentImpl(newText);

    }

    @Override
    public String commonSubText(TextDocument other) {
        return null;
    }

    public static void main(String[] args) {
        TextDocument text = new TextDocumentImpl("hahahahaha haha jij df ae");
        TextDocument t2 = text.wrap(5);
        System.out.println(t2.getText());
    }
}
