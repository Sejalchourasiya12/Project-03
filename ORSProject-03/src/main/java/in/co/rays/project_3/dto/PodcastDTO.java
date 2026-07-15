package in.co.rays.project_3.dto;

public class PodcastDTO extends BaseDTO {

    private Long id;
    private String podcastCode;
    private String podcastTitle;
    private String hostName;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPodcastCode() {
        return podcastCode;
    }

    public void setPodcastCode(String podcastCode) {
        this.podcastCode = podcastCode;
    }

    public String getPodcastTitle() {
        return podcastTitle;
    }

    public void setPodcastTitle(String podcastTitle) {
        this.podcastTitle = podcastTitle;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return status;
    }
}