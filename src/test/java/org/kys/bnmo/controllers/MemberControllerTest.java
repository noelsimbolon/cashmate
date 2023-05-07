package org.kys.bnmo.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kys.bnmo.model.Member;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class MemberControllerTest {

    private static DataStore dataStore;
    private static MemberController memberController;

    @BeforeAll
    static void setUp() throws IOException {
        dataStore = new DataStore();
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);

        memberController = new MemberController();
    }

    @Test
    void fetchAll() {
        // Arrange
        var members = new ArrayList<Member>();
        var member1 = new Member("Member 1", "10293826451", "Member");
        var member2 = new Member("Member 2", "12038264281", "VIP");
        members.add(member1);
        members.add(member2);

        // Act
        dataStore.writeData("member", members);
        ArrayList<Member> fetchedMembers = memberController.fetchAll();

        // Asert
        Assertions.assertEquals(member1.getCustomerID(), fetchedMembers.get(0).getCustomerID());
        Assertions.assertEquals(member2.getCustomerID(), fetchedMembers.get(1).getCustomerID());
    }

    @Test
    void fetchByID() {
        // Arrange
        var members = new ArrayList<Member>();
        var member1 = new Member("John", "10293826451", "Member");
        var member2 = new Member("Robert", "12038264281", "VIP");
        members.add(member1);
        members.add(member2);

        // Act
        dataStore.writeData("member", members);
        ArrayList<Member> fetchedMembers = memberController.fetchByID(member1.getCustomerID());

        // Asert
        Assertions.assertEquals(member1.getCustomerID(), fetchedMembers.get(0).getCustomerID());
    }

    @Test
    void fetchByName() {
        // Arrange
        var members = new ArrayList<Member>();
        var member1 = new Member("John", "10293826451", "Member");
        var member2 = new Member("Robert", "12038264281", "VIP");
        var member3 = new Member("John", "16284172123", "VIP");
        members.add(member1);
        members.add(member2);
        members.add(member3);

        // Act
        dataStore.writeData("member", members);
        ArrayList<Member> fetchedMembers = memberController.fetchByName("John");

        // Asert
        Assertions.assertEquals(member1.getName(), fetchedMembers.get(0).getName());
        Assertions.assertEquals(member3.getName(), fetchedMembers.get(1).getName());
    }

    @Test
    void fetchByStatus() {
        // Arrange
        var members = new ArrayList<Member>();
        var member1 = new Member("John", "10293826451", "Member");
        var member2 = new Member("Robert", "12038264281", "VIP");
        var member3 = new Member("John", "16284172123", "VIP");
        member1.deactivate();
        member3.deactivate();
        members.add(member1);
        members.add(member2);
        members.add(member3);

        // Act
        dataStore.writeData("member", members);
        ArrayList<Member> fetchedMembers = memberController.fetchByStatus("Inactive");

        // Asert
        Assertions.assertEquals(member1.getCustomerID(), fetchedMembers.get(0).getCustomerID());
        Assertions.assertEquals(member3.getCustomerID(), fetchedMembers.get(1).getCustomerID());
    }

    @Test
    void fetchByMemberLevel() {
        // Arrange
        var members = new ArrayList<Member>();
        var member1 = new Member("John", "10293826451", "Member");
        var member2 = new Member("Robert", "12038264281", "VIP");
        var member3 = new Member("John", "16284172123", "VIP");
        members.add(member1);
        members.add(member2);
        members.add(member3);

        // Act
        dataStore.writeData("member", members);
        ArrayList<Member> fetchedMembers = memberController.fetchByMemberLevel("VIP");

        // Asert
        Assertions.assertEquals(member2.getCustomerID(), fetchedMembers.get(0).getCustomerID());
        Assertions.assertEquals(member3.getCustomerID(), fetchedMembers.get(1).getCustomerID());
    }

    @Test
    void save() {
        // Arrange
        var members = new ArrayList<Member>();
        var member1 = new Member("John", "10293826451", "Member");
        var member2 = new Member("Robert", "12038264281", "VIP");
        var member3 = new Member("John", "16284172123", "VIP");
        members.add(member1);
        members.add(member2);
        members.add(member3);

        // Act
        memberController.save(members);
        ArrayList<Member> fetchedMembers = dataStore.readData("member", Member.class);

        // Asert
        Assertions.assertEquals(member1.getCustomerID(), fetchedMembers.get(0).getCustomerID());
        Assertions.assertEquals(member2.getCustomerID(), fetchedMembers.get(1).getCustomerID());
        Assertions.assertEquals(member3.getCustomerID(), fetchedMembers.get(2).getCustomerID());
    }

    @Test
    void updateById() {
        // Arrange
        var members = new ArrayList<Member>();
        var member1 = new Member("John", "10293826451", "Member");
        members.add(member1);

        // Act
        dataStore.writeData("member", members);
        memberController.updateById(member1.getCustomerID(), "Peter", "1728462512");
        ArrayList<Member> fetchedMembers = dataStore.readData("member", Member.class);

        // Asert
        Assertions.assertEquals("Peter", fetchedMembers.get(0).getName());
        Assertions.assertEquals("1728462512", fetchedMembers.get(0).getPhoneNumber());
    }

    @Test
    void deleteById() {
        // Arrange
        var members = new ArrayList<Member>();
        var member1 = new Member("John", "10293826451", "Member");
        var member2 = new Member("Robert", "12038264281", "VIP");
        members.add(member1);
        members.add(member2);

        // Act
        dataStore.writeData("member", members);
        memberController.deleteById(member1.getCustomerID());
        ArrayList<Member> fetchedMembers = dataStore.readData("member", Member.class);

        // Asert
        Assertions.assertEquals(member2.getCustomerID(), fetchedMembers.get(0).getCustomerID());
    }
}
