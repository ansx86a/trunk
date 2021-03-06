package dao.domain;

public class HcomicPool {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hcomicpool.comicid
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    private Integer comicid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hcomicpool.title1
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    private String title1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hcomicpool.url
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hcomicpool.absurl
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    private String absurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hcomicpool.file_path
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    private String filePath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hcomicpool.absdownload
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    private String absdownload;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hcomicpool.downloaded
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    private Integer downloaded;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hcomicpool.type
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    private Integer type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hcomicpool.comicid
     *
     * @return the value of hcomicpool.comicid
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public Integer getComicid() {
        return comicid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hcomicpool.comicid
     *
     * @param comicid the value for hcomicpool.comicid
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public void setComicid(Integer comicid) {
        this.comicid = comicid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hcomicpool.title1
     *
     * @return the value of hcomicpool.title1
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public String getTitle1() {
        return title1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hcomicpool.title1
     *
     * @param title1 the value for hcomicpool.title1
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public void setTitle1(String title1) {
        this.title1 = title1 == null ? null : title1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hcomicpool.url
     *
     * @return the value of hcomicpool.url
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hcomicpool.url
     *
     * @param url the value for hcomicpool.url
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hcomicpool.absurl
     *
     * @return the value of hcomicpool.absurl
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public String getAbsurl() {
        return absurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hcomicpool.absurl
     *
     * @param absurl the value for hcomicpool.absurl
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public void setAbsurl(String absurl) {
        this.absurl = absurl == null ? null : absurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hcomicpool.file_path
     *
     * @return the value of hcomicpool.file_path
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hcomicpool.file_path
     *
     * @param filePath the value for hcomicpool.file_path
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hcomicpool.absdownload
     *
     * @return the value of hcomicpool.absdownload
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public String getAbsdownload() {
        return absdownload;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hcomicpool.absdownload
     *
     * @param absdownload the value for hcomicpool.absdownload
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public void setAbsdownload(String absdownload) {
        this.absdownload = absdownload == null ? null : absdownload.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hcomicpool.downloaded
     *
     * @return the value of hcomicpool.downloaded
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public Integer getDownloaded() {
        return downloaded;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hcomicpool.downloaded
     *
     * @param downloaded the value for hcomicpool.downloaded
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public void setDownloaded(Integer downloaded) {
        this.downloaded = downloaded;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hcomicpool.type
     *
     * @return the value of hcomicpool.type
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hcomicpool.type
     *
     * @param type the value for hcomicpool.type
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    public void setType(Integer type) {
        this.type = type;
    }
}