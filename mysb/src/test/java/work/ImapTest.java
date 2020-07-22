package work;

import org.junit.Test;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class ImapTest {

    @Test
    public void test() {
        System.out.println("ok");
    }

    /**
     * https://myaccount.google.com/lesssecureapps?pli=1
     * 要先去我的帳戶，把低安全性應用程式存取權給打開，以下的程式才會生效
     */
    @Test
    public void getResult1() {
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "bnsx86b@gmail.com", "graffiti");

            //if you want mail from specified folder, just change change folder name
            //Folder inbox = store.getFolder("[Gmail]/Drafts");
            Folder inbox = store.getFolder("inbox");

            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
            System.out.println(store.getDefaultFolder().list("*"));
            javax.mail.Folder[] folders = store.getDefaultFolder().list("*");

            for (javax.mail.Folder folder : folders) {
                if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
                    System.out.println(folder.getName());
                }
            }

            System.out.println("Total Messages:-: " + messageCount);
            javax.mail.Message[] messages = inbox.getMessages();

            System.out.println("------------------------------");
            System.out.println("messages: " + messages.toString());
            for (int i = 0; i < messages.length; i++) {
                System.out.println("getResult1: " + messages[i].getSubject());
            }
            inbox.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 以下程式在登入時會被server斷線，先保留做為記錄
     */
    @Test
    public void test2() {

        try {
            Properties props = new Properties();
            props.put("mail.imap.host", "imap.gmail.com");
            props.put("mail.imap.port", "993");
            props.put("mail.store.protocol", "imap");
            props.put("mail.debug", "false");
            Session session = Session.getInstance(props);
            Store store = session.getStore("imap");
            store.connect("bnsx86b@gmail.com", "graffiti");

            //if you want mail from specified folder, just change change folder name
            //Folder inbox = store.getFolder("[Gmail]/Drafts");
            Folder inbox = store.getFolder("inbox");

            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
            System.out.println(store.getDefaultFolder().list("*"));
            javax.mail.Folder[] folders = store.getDefaultFolder().list("*");

            for (javax.mail.Folder folder : folders) {
                if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
                    System.out.println(folder.getName());
                }
            }

            System.out.println("Total Messages:-: " + messageCount);
            javax.mail.Message[] messages = inbox.getMessages();

            System.out.println("------------------------------");
            System.out.println("messages: " + messages.toString());
            for (int i = 0; i < messages.length; i++) {
                System.out.println("getResult1: " + messages[i].getSubject());
            }
            inbox.close(true);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        class Test3 {
            private Properties getServerProperties(String protocol, String host, String port) {
                Properties properties = new Properties();
                // server setting
                properties.put(format("mail.%s.host", protocol), host);
                properties.put(format("mail.%s.port", protocol), port);
                // SSL setting
                properties.setProperty(format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
                properties.setProperty(format("mail.%s.socketFactory.fallback", protocol), "false");
                properties.setProperty(format("mail.%s.socketFactory.port", protocol), String.valueOf(port));
                return properties;
            }

            private void downloadEmails(String protocol, String host, String port,
                                        String userName, String password) {
                Properties properties = getServerProperties(protocol, host, port);
                Session session = Session.getDefaultInstance(properties);

                try {
                    // connects to the message store
                    Store store = session.getStore(protocol);
                    store.connect(userName, password);

                    // opens the inbox folder
                    Folder folderInbox = store.getFolder("INBOX");
                    //folderInbox.open(Folder.READ_ONLY);
                    folderInbox.open(Folder.READ_WRITE);

                    int messageCount = folderInbox.getMessageCount();
                    // fetches new messages from server
                    List<Message> messages = Arrays.asList(folderInbox.getMessages(1, 10));
                    List<Message> messages2 = messages.stream().filter(this::isThisMsg).collect(toList());

                    for (int i = 0; i < messages2.size(); i++) {
                        Message msg = messages2.get(i);
                        Address[] fromAddress = msg.getFrom();
                        String from = fromAddress[0].toString();
                        String subject = msg.getSubject();
                        String toList = parseAddresses(msg.getRecipients(Message.RecipientType.TO));
                        String ccList = parseAddresses(msg.getRecipients(Message.RecipientType.CC));
                        String sentDate = msg.getSentDate().toString();

                        String contentType = msg.getContentType();
                        String messageContent = "";

                        if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                            try {
                                Object content = msg.getContent();
                                if (content != null) {
                                    messageContent = content.toString();
                                }
                            } catch (Exception ex) {
                                messageContent = "[Error downloading content]";
                                ex.printStackTrace();
                            }
                        }

                        // print out details of each message
                        System.out.println("Message #" + (i + 1) + ":");
                        System.out.println("\t From: " + from);
                        System.out.println("\t To: " + toList);
                        System.out.println("\t CC: " + ccList);
                        System.out.println("\t Subject: " + subject);
                        System.out.println("\t Sent Date: " + sentDate);
                        System.out.println("\t Message: " + messageContent);
                        System.out.println(msg.getFlags());
                        msg.setFlag(Flags.Flag.DELETED,true);
                    }

                    // disconnect
                    folderInbox.close(false);
                    store.close();
                } catch (NoSuchProviderException ex) {
                    System.out.println("No provider for protocol: " + protocol);
                    ex.printStackTrace();
                } catch (MessagingException ex) {
                    System.out.println("Could not connect to the message store");
                    ex.printStackTrace();
                }
            }

            private boolean isThisMsg(Message message) {
                try {
                    return message.getSubject().contains("RiftCat 5th Ann");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return false;

            }

            private String parseAddresses(Address[] address) {
                String listAddress = "";

                if (address != null) {
                    for (int i = 0; i < address.length; i++) {
                        listAddress += address[i].toString() + ", ";
                    }
                }
                if (listAddress.length() > 1) {
                    listAddress = listAddress.substring(0, listAddress.length() - 2);
                }

                return listAddress;
            }

        }

        // for POP3
//        String protocol = "pop3";
//        String host = "pop.gmail.com";
//        String port = "995";

        // for IMAP
        String protocol = "imap";
        String host = "imap.gmail.com";
        String port = "993";


        String userName = "bnsx86b@gmail.com";
        String password = "graffiti";

        new Test3().downloadEmails(protocol, host, port, userName, password);
    }


}
