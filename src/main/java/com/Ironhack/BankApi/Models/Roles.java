package com.Ironhack.BankApi.Models;

import javax.persistence.*;

@Entity
public class Roles {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String role;
        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        public Roles(String role, User user) {
            this.role = role;
            this.user = user;
        }

        public Roles() {

        }

        public Roles(String role) {
            this.role = role;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
}
