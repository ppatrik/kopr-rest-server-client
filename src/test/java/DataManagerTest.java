import org.junit.Assert;
import org.junit.Test;
import sk.upjs.kopr2014.ppatrik.rest.logic.DataManager;
import sk.upjs.kopr2014.ppatrik.rest.logic.Dokument;

import java.util.UUID;

public class DataManagerTest {

    @Test
    public void readWriteTest() {
        DataManager dm = new DataManager();

        System.out.println(dm.list());

        Assert.assertEquals(0, dm.length());

        UUID uuid = null;
        try {
            uuid = dm.create("Nazov", "Autor", "Jazyk", "Text", "Kluc", "Data");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertEquals(1, dm.length());

        Assert.assertNotNull(uuid);
        UUID uuid1 = UUID.fromString(uuid.toString()); // aby to bol iny objekt
        Dokument dokument = null;
        try {
            dokument = dm.read(uuid1);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        Assert.assertNotNull(dokument);

        Assert.assertTrue(uuid.equals(dokument.getUuid()));
        Assert.assertTrue(uuid1.equals(dokument.getUuid()));
        Assert.assertTrue("Nazov".equals(dokument.getNazov()));
        Assert.assertTrue("Autor".equals(dokument.getAutor()));
        Assert.assertTrue("Jazyk".equals(dokument.getJazyk()));
        Assert.assertTrue("Text".equals(dokument.getText()));
        Assert.assertTrue("Kluc".equals(dokument.getKluc()));
    }

    @Test
    public void tryToAddTwiceTest() {
        DataManager dm = new DataManager();

        Assert.assertEquals(dm.length(), 0);

        try {
            dm.create("Nazov", "Autor", "Jazyk", "Text", "Kluc", "Data");
            dm.create("Nazov1", "Autor", "Jazyk", "Text", "Kluc", "Data");
            dm.create("Nazov1", "Autor", "Jazyk", "Text", "Kluc", "Data");
        } catch (Exception e) {
            Assert.assertTrue("sk.upjs.kopr2014.ppatrik.rest.logic.Dokument uz existuje!".equals(e.getMessage()));
        }

        Assert.assertEquals(dm.length(), 2);
    }

    @Test
    public void ifExistsTest() {
        DataManager dm = new DataManager();
        Assert.assertEquals(dm.length(), 0);
        UUID uuid = UUID.randomUUID();

        try {
            dm.read(uuid);
        } catch (Exception e) {
            Assert.assertTrue("sk.upjs.kopr2014.ppatrik.rest.logic.Dokument nebol nájdený".equals(e.getMessage()));
        }

    }


}
