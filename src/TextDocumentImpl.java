/**
 * Created by nypham on 6/17/17.
 */
import javax.xml.bind.SchemaOutputResolver;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
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
            }
            if (currentLineWidth != 0) {
                newText += newText;
            }
            if (currWord.length() > columnWidth) {
                int thiscurr = 0;
                int numofLine = currWord.length()/columnWidth;
                int tracker = columnWidth - 1;
                for (int j = 0; j < numofLine; j++) {
                    newText += currWord.substring(thiscurr, tracker) + "-" + "\n";
                    thiscurr += tracker;
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

    private List<String> helper (String word, int columnWidth, int currentLineWidth) {
        List<String> result = new ArrayList<>();
        int beginIn = 0;
        int endIn = columnWidth - 1 - currentLineWidth;
        if (currentLineWidth > 0) {
            result.add(word.substring(beginIn, endIn) + "-\n");
            word = word.substring(endIn);
        }
        while (word.length() > columnWidth) {
            result.add(word.substring(0, columnWidth - 1) + "-\n");
            word = word.substring(columnWidth - 1);
        }
        result.add(word);
        return result;
    }

    public static void main(String[] args) {
        TextDocument text = new TextDocumentImpl("chau len ba chau vo jjgkkjhuu oiji kb gfd lknlkn kjjljb ");
        TextDocument t2 = text.wrap(5);
        System.out.println(t2.getText());
    }
}
