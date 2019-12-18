package com.example.engineeringpractical.mdoel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListResponse {
    @SerializedName("hits")
    private List<UserHits> hits;

    @SerializedName("nbPages")
    private int nbPages;

    public List<UserHits> getHits() {
        return hits;
    }

    public void setHits(List<UserHits> hits) {
        this.hits = hits;
    }

    public int getNbPages() {
        return nbPages;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }


    public static class UserHits {
        @SerializedName("title")
        String title;

        @SerializedName("created_at")
        String created_at;

        Boolean isSelected = false;

        public Boolean getSelected() {
            return isSelected;
        }

        public void setSelected(Boolean selected) {
            isSelected = selected;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}


