/**
 * Created by nypham on 6/17/17.
 */
interface TextDocument {
    int getWordCount();
    String getText();
    TextDocument wrap(int columnWidth);
    String commonSubText(TextDocument other);
}
