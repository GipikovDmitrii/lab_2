package database;

import javax.xml.bind.*;
import java.io.File;

public class XmlManager {

    private static final String XML_NAME = "database.xml";

    public XmlManager() {
    }

    public void saveXml(UserList userList) {
        try {
            JAXBContext context = JAXBContext.newInstance(UserList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(userList, new File(XML_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public UserList loadXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(UserList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (UserList) unmarshaller.unmarshal(new File(XML_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new UserList();
    }
}
