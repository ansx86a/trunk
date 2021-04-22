package filesService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import utils.Utils;

/**
 * 注意ZIP4J的一個特點，如果zip檔已存在，他不會把他zip蓋過去，而是在zip檔中加入此次的東西檔<br>
 * 感覺有vfs的概念一樣<br>
 * 新的zip 3rd有空試試 https://github.com/zeroturnaround/zt-zip
 *
 * @author ai
 */
public class ZIP4j壓縮和解壓縮 {

    public static void 目錄壓ZIP(File f, boolean deleteAfterZip) throws Exception {
        if (f.isFile()) {
            System.out.println("不是目錄");
            new String();
            return;
        }
        // 再來是把FileList放進去
        ZipFile zipFile = new ZipFile(f.toPath().resolveSibling(f.getName() + ".zip").toFile());
        ZipParameters parameters = new ZipParameters();
        設壓縮率:
        {
            // 沒設好像就會預設成不壓縮
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            // parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
            // parameters.setCompressionMethod(Zip4jConstants.COMP_STORE);
        }
        設密碼:
        {
            // parameters.setEncryptFiles(true);
            // parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            // parameters.setPassword("1234");
        }
        ArrayList<File> fileList = new ArrayList<>();
        // 很貼心的設計
        for (File f2 : f.listFiles()) {
            if (f2.isFile()) {
                // zipFile.addFile(f2, parameters);
                fileList.add(f2);
            } else {
                // 這裡有取去File的方式，但是我不知道怎麼設到zip中的路徑去
                // ArrayList<File> l = Zip4jUtil.getFilesInDirectoryRec(f2, true);//
                // fileList.addAll(l);
                zipFile.addFolder(f2, parameters);
            }
        }

        if (fileList.size() > 0) {
            zipFile.addFiles(fileList, parameters);
        }

        if (deleteAfterZip) {
            FileUtils.deleteDirectory(f);
        }
    }

    public static void 解壓到目錄(File zipFile, File extDir) {
        try {
            ZipFile zfile = new ZipFile(zipFile.getAbsolutePath());
            ;
            zfile.extractAll(extDir.getAbsolutePath());
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    // 在工作上研究出來的，本機還未實測過
    public byte[] 直接從byteArray產生zip檔(List<Pair<String, byte[]>> nameDataList) {
        if (CollectionUtils.isEmpty(nameDataList)) {
            return null;
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (Pair<String, byte[]> nameData : nameDataList) {
                ZipParameters p = new ZipParameters();
                p.setCompressionLevel(Zip4jConstants.COMP_STORE);
                p.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                p.setSourceExternalStream(true);
                p.setFileNameInZip(nameData.getLeft());
                zos.putNextEntry(null, p);
                zos.write(nameData.getRight());
                zos.closeEntry();
            }
            zos.finish();
            zos.close();
            return baos.toByteArray();
        } catch (IOException | ZipException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 在工作上研究出來的，本機還末實測過
    public static void 同把byte壓到已存在的zip檔中(File targetZipFile, String fileName, byte[] bs) throws ZipException {
        //一般來說，zip4j是用file來壓zip檔，而下面是用byte來替換file
        ZipFile zipFile = new ZipFile(targetZipFile);
        ZipParameters p = new ZipParameters();
        p.setSourceExternalStream(true);
        p.setFileNameInZip(fileName);
        zipFile.addStream(new ByteArrayInputStream(bs), p);

    }

    public static void main(String args[]) throws Exception {
        File f = Utils.getResourceFromRoot("filesService");
        System.out.println(f);
        目錄壓ZIP(f, false);
        File zipFile = Utils.getResourceFromRoot("filesService.zip");
        File extDir = Utils.getResourceFromRoot("filesService壓解縮");
        System.out.println(extDir);
        解壓到目錄(zipFile, extDir);

        System.out.println("end");
    }

}
