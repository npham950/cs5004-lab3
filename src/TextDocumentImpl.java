/**
 * Created by nypham on 6/17/17.
 */
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
        verifyInput(columnWidth);
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
                if (condition(currentLineWidth, columnWidth, wordTracker)) {
                    newText += " ";
                    currentLineWidth++;
                }
                wordTracker++;
                continue;
            } else {
                if (currWord.length() <= columnWidth) {
                    newText += "\n" + currWord;
                    currentLineWidth = currWord.length();
                    if (condition(currentLineWidth, columnWidth, wordTracker)) {
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
                    if (condition(currentLineWidth, columnWidth, wordTracker)) {
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
        int index = 0;
        int startIn = 0;
        String thisString = this.getText().toLowerCase();
        String otherString = other.getText().toLowerCase();
        if (thisString.isEmpty() || otherString.isEmpty()) {
            return "";
        }
        int [][] number = new int [thisString.length()][otherString.length()];
        int maxLength = 0;
        for (int i = 0; i < thisString.length(); i++) {
            for (int j = 0; j < otherString.length(); j++) {
                if (thisString.charAt(i) == otherString.charAt(j)) {
                    if (i == 0 || j == 0) {
                        number [i][j] = 1;
                    }else {
                        number[i][j] = number[i-1][j-1] + 1;
                    }
                }
                if (number [i][j] > maxLength) {
                    maxLength = number [i][j];
                    index = i;
                }
            }
        }
        startIn = index - maxLength + 1;
        return this.getText().substring(startIn, Math.min(index + 1, this.getText().length()));
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

    private boolean condition(int currentLineWidth, int columnWidth, int wordTracker) {
        return currentLineWidth != columnWidth && wordTracker != wordArr.size() - 1;
    }

    private void verifyInput(int columnWidth) {
        if (columnWidth < 2) {
            throw new IllegalArgumentException("Invalid variable, the variable should be greater than 1");
        }
    }

    public static void main(String[] args) {
        TextDocument text = new TextDocumentImpl("Today is a Thursday");
        TextDocument text2 = new TextDocumentImpl("This is a Thursday evening event");
        System.out.println(text.commonSubText(text2));
    }
}
