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
        int wordTracker = 0;
        String newText = "";
        int currentLineWidth = 0;
        while (wordTracker < wordArr.size()) {
            String currWord = wordArr.get(wordTracker);
            if (currentLineWidth == columnWidth) {
                newText += "\n";
                currentLineWidth = 0;
            }
            if (currentLineWidth + currWord.length() <= columnWidth) {
                newText += currWord;
                currentLineWidth += currWord.length();
                if (currentLineWidth != columnWidth && wordTracker != wordArr.size() - 1) {
                    newText += " ";
                    currentLineWidth++;
                }
                wordTracker++;
                continue;
            } else {
                if (currWord.length() <= columnWidth) {
                    newText += "\n" + currWord;
                    currentLineWidth = currWord.length();
                    if (currentLineWidth != columnWidth && wordTracker != wordArr.size() - 1) {
                        newText += " ";
                        currentLineWidth++;
                    }
                }else  {
                    List<String> divword = helper(currWord, columnWidth, currentLineWidth);
                    while (divword.size() > 1) {
                        newText += divword.get(0);
                        divword.remove(0);
                    }
                    newText += divword.get(0);
                    currentLineWidth = divword.get(0).length();
                    if (currentLineWidth != columnWidth && wordTracker != wordArr.size() - 1) {
                        newText += " ";
                        currentLineWidth++;
                    }
                }
                wordTracker++;
                continue;
            }
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
        TextDocument text = new TextDocumentImpl("This is a random string.");
        TextDocument t2 = text.wrap(6);
        System.out.println(t2.getText());
    }
}
