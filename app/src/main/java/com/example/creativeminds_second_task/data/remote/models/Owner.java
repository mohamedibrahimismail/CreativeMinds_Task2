
package com.example.creativeminds_second_task.data.remote.models;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("id")
    @Expose
    private Integer owner_id;
    @SerializedName("node_id")
    @Expose
    private String owner_nodeId;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("gravatar_id")
    @Expose
    private String gravatarId;
    @SerializedName("url")
    @Expose
    private String owner_url;
    @SerializedName("html_url")
    @Expose
    private String owner_htmlUrl;
    @SerializedName("followers_url")
    @Expose
    private String followersUrl;
    @SerializedName("following_url")
    @Expose
    private String followingUrl;
    @SerializedName("gists_url")
    @Expose
    private String gistsUrl;
    @SerializedName("starred_url")
    @Expose
    private String starredUrl;
    @SerializedName("subscriptions_url")
    @Expose
    private String subscriptionsUrl;
    @SerializedName("organizations_url")
    @Expose
    private String organizationsUrl;
    @SerializedName("repos_url")
    @Expose
    private String reposUrl;
    @SerializedName("events_url")
    @Expose
    private String owner_eventsUrl;
    @SerializedName("received_events_url")
    @Expose
    private String receivedEventsUrl;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("site_admin")
    @Expose
    private Boolean siteAdmin;

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public void setOwner_nodeId(String owner_nodeId) {
        this.owner_nodeId = owner_nodeId;
    }

    public void setOwner_url(String owner_url) {
        this.owner_url = owner_url;
    }

    public void setOwner_htmlUrl(String owner_htmlUrl) {
        this.owner_htmlUrl = owner_htmlUrl;
    }

    public void setOwner_eventsUrl(String owner_eventsUrl) {
        this.owner_eventsUrl = owner_eventsUrl;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public String getOwner_nodeId() {
        return owner_nodeId;
    }

    public String getOwner_url() {
        return owner_url;
    }

    public String getOwner_htmlUrl() {
        return owner_htmlUrl;
    }

    public String getOwner_eventsUrl() {
        return owner_eventsUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getowner_id() {
        return owner_id;
    }

    public void setId(Integer id) {
        this.owner_id = id;
    }

    public String getowner_nodeId() {
        return owner_nodeId;
    }

    public void setNodeId(String nodeId) {
        this.owner_nodeId = nodeId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public String getUrl() {
        return owner_url;
    }

    public void setUrl(String url) {
        this.owner_url = url;
    }

    public String getHtmlUrl() {
        return owner_htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.owner_htmlUrl = htmlUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    public String getGistsUrl() {
        return gistsUrl;
    }

    public void setGistsUrl(String gistsUrl) {
        this.gistsUrl = gistsUrl;
    }

    public String getStarredUrl() {
        return starredUrl;
    }

    public void setStarredUrl(String starredUrl) {
        this.starredUrl = starredUrl;
    }

    public String getSubscriptionsUrl() {
        return subscriptionsUrl;
    }

    public void setSubscriptionsUrl(String subscriptionsUrl) {
        this.subscriptionsUrl = subscriptionsUrl;
    }

    public String getOrganizationsUrl() {
        return organizationsUrl;
    }

    public void setOrganizationsUrl(String organizationsUrl) {
        this.organizationsUrl = organizationsUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public String getEventsUrl() {
        return owner_eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.owner_eventsUrl = eventsUrl;
    }

    public String getReceivedEventsUrl() {
        return receivedEventsUrl;
    }

    public void setReceivedEventsUrl(String receivedEventsUrl) {
        this.receivedEventsUrl = receivedEventsUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSiteAdmin() {
        return siteAdmin;
    }

    public void setSiteAdmin(Boolean siteAdmin) {
        this.siteAdmin = siteAdmin;
    }

}
