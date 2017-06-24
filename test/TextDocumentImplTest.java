import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;

/**
 * Junit tests for TextDocument.
 */
public class TextDocumentImplTest {
    TextDocument doc1;
    TextDocument doc2;
    TextDocument doc3;
    TextDocument doc4;
    TextDocument doc5;
    TextDocument doc6;
    TextDocument doc7;
    TextDocument doc8;

    /**
     * Creates 8 TextDocument objects for testings.
     */
    @Before
    public void setUp() throws Exception {
        this.doc1 = new TextDocumentImpl("This is a random string.\n");
        this.doc2 = new TextDocumentImpl("Testing123456789\n");
        this.doc3 = new TextDocumentImpl("Today is a Thursday");
        this.doc4 = new TextDocumentImpl("This is a Thursday evening event");
        this.doc5 = new TextDocumentImpl("12345678");
        this.doc6 = new TextDocumentImpl("abracadabra shazam");
        this.doc7 = new TextDocumentImpl("internationalization");
        this.doc8 = new TextDocumentImpl("sha abracadabraabracadabra shazam");
    }

    @Test
    public void testGetWordCount() throws Exception {
        Assert.assertEquals(5, this.doc1.getWordCount());
        Assert.assertEquals(3, this.doc8.getWordCount());
        Assert.assertEquals(1, this.doc5.getWordCount());
    }

    @Test
    public void testGetText() throws Exception {
        Assert.assertEquals("This is a random string.\n", this.doc1.getText());
        Assert.assertEquals("12345678", this.doc5.getText());
    }

    @Test
    public void testWrap() throws Exception {
        Assert.assertEquals("abrac-\nadabra\nshazam", this.doc6.wrap(6).getText());
        Assert.assertEquals("inter-\nnatio-\nnaliz-\nation", this.doc7.wrap(6).getText());
        Assert.assertEquals("This is a \nrandom \nstring.", this.doc1.wrap(10).getText());
        Assert.assertEquals("sha a-\nbraca-\ndabra-\nabrac-\nadabra\nshazam",
                this.doc8.wrap(6).getText());
        Assert.assertEquals("This is\na \nrandom \nstring.", this.doc1.wrap(7).getText());
        Assert.assertEquals("This \nis a \nrandom\nstrin-\ng.", this.doc1.wrap(6).getText());
    }

    @Test
    public void testWrapIllegalInput() throws Exception {
        try {
            this.doc3.wrap(1);
            Assert.fail("Exception not thrown when column width is less than 2");
        } catch (IllegalArgumentException e) {
            // Pass test
        }

        try {
            this.doc4.wrap(-1);
            Assert.fail("Exception not thrown when column width is less than 2");
        } catch (IllegalArgumentException e) {
            // Pass test
        }

        try {
            this.doc8.wrap(-0);
            Assert.fail("Exception not thrown when column width is less than 2");
        } catch (IllegalArgumentException e) {
            // Pass test
        }
    }

    @Test
    public void testCommonSubText() throws Exception {
        Assert.assertEquals(" is a Thursday", doc3.commonSubText(doc4));
        Assert.assertEquals(" is a Thursday", doc4.commonSubText(doc3));
        Assert.assertEquals(" is a ", doc3.commonSubText(doc1));
        Assert.assertEquals("", doc5.commonSubText(doc1));
        Assert.assertEquals("abracadabra shazam", doc6.commonSubText(doc8));
        Assert.assertEquals("abracadabra shazam", doc8.commonSubText(doc6));
    }
}
