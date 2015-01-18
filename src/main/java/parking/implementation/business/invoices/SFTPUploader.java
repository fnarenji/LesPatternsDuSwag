package parking.implementation.business.invoices;

import com.jcraft.jsch.*;
import parking.api.business.invoices.Invoice;
import parking.api.business.invoices.InvoiceExporter;

import java.io.*;

/**
 * Created by Thomas on 18/01/2015.
 */
public class SFTPUploader implements InvoiceExporter {
    private InvoiceExporter invoiceExporter;

    private String password = "javainvoiceparking2015";
    private String server = "unicorn.ovh";
    private String login = "invoice_java";
    private Integer port = 4242;

    private String workingDirectory = "invoices/";

    public String getLogin() {
        return login;
    }
    public Integer getPort() {
        return port;
    }
    public String getWorkingDirectory() {
        return workingDirectory;
    }
    public String getServer() {
        return server;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setServer(String server) {
        this.server = server;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPort(Integer port) {
        this.port = port;
    }

    public SFTPUploader(InvoiceExporter invoiceExporter){
        this.invoiceExporter = invoiceExporter;
    }

    private void upload(String filename){
        try{
            JSch jsch = new JSch();
            Session session = jsch.getSession(login, server, port);
            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp)channel;
            channelSftp.cd(workingDirectory);
            File f = new File(filename);

            channelSftp.put(new FileInputStream(f), f.getName());

            f.delete();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Export (in HTML) the invoice and upload it on a server.
     * @return String url : the url of the invoice
     */
    @Override
    public String export() {
        String content = invoiceExporter.export();
        int invoiceNumber = invoiceExporter.getInvoice().getInvoiceNumber();

        String extension = (content.contains("html")) ? ".html" : ".txt";

        String fileName = "save/invoices/" + invoiceNumber + extension;
        OutputStream os = null;

        try {
            os = new FileOutputStream(new File(fileName));

            byte[] contentInBytes = content.getBytes();

            os.write(contentInBytes);
            os.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String url = "http://" + server + "/" + workingDirectory + invoiceNumber + ".html";
        return url;
    }

    @Override
    public Invoice getInvoice() {
        return invoiceExporter.getInvoice();
    }
}
