package org.kys.bnmo.controllers;

import org.kys.bnmo.model.Member;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MemberController {
    private DataStore dataStore;
    private String fileName;

    public MemberController() {
        dataStore = new DataStore();
        fileName = "member";
    }

    public ArrayList<Member> fetchAll() {
        return dataStore.readData(fileName, Member.class);
    }

    public ArrayList<Member> fetchByID(int id) {
        return (ArrayList<Member>) fetchAll().stream()
                .filter(m -> m.getCustomerID() == id)
                .collect(Collectors.toList());
    }

     public ArrayList<Member> fetchByName(String name) {
         return (ArrayList<Member>) fetchAll().stream()
                 .filter(m -> m.getName().equals(name))
                 .collect(Collectors.toList());
     }

     public ArrayList<Member> fetchByStatus(String status) {
         return (ArrayList<Member>) fetchAll().stream()
                 .filter(m -> m.getStatus().equals(status))
                 .collect(Collectors.toList());
     }

     public ArrayList<Member> fetchByMemberLevel(String level) {
         return (ArrayList<Member>) fetchAll().stream()
                 .filter(m -> m.getMemberLevel().equals(level))
                 .collect(Collectors.toList());
     }

     public void save(ArrayList<Member> data) {
         dataStore.writeData("member", data);
     }

     public void updateById(int id, String name, String phoneNumber) {
         ArrayList<Member> data = fetchAll();

         for (Member member: data) {
             if (member.getCustomerID() == id) {
                 member.setName(name);
                 member.setPhoneNumber(phoneNumber);
             }
         }

         save(data);
     }
}
