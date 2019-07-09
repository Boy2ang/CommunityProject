package life.majiang.community.dto;

public class GitHupUser {
    private String name; //名字
    private String bid; //描述
    private Long id; //编号
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "GitHupUser{" +
                "name='" + name + '\'' +
                ", bid='" + bid + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}
