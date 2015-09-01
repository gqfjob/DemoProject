package com.andaily.infrastructure.hibernate;

import com.andaily.domain.shared.GuidGenerator;
import com.andaily.domain.shared.security.SecurityUtils;
import com.andaily.domain.user.*;
import com.andaily.infrastructure.AbstractRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.AssertJUnit.*;

/**
 * @author Shengzhao Li
 */
public class UserRepositoryHibernateTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testSystemSetting() {

        final SystemSetting setting = userRepository.findByGuid(GuidGenerator.generate(), SystemSetting.class);
        assertNull(setting);

        SystemSetting setting1 = new SystemSetting();
        userRepository.saveOrUpdate(setting1);

        fullClean();

        final SystemSetting setting2 = userRepository.findByGuid(setting1.guid(), SystemSetting.class);
        assertNotNull(setting2);

    }

    @Test
    public void findSystemSetting() {

        final SystemSetting setting = userRepository.findSystemSetting();
        assertNotNull(setting);
        userRepository.saveOrUpdate(setting);

        final SystemSetting setting2 = userRepository.findSystemSetting();
        assertNotNull(setting2);
        assertEquals(setting, setting2);


    }

    @Test
    public void findByGuid() throws Exception {

        User user = userRepository.findByGuid("oood", User.class);
        assertNull(user);

        user = newUser();

        fullClean();

        user = userRepository.findByGuid(user.guid(), User.class);
        assertNotNull(user);
        assertNotNull(user.email());


        final User user2 = userRepository.findByUsername(user.username());
        assertNotNull(user2);
    }

    @Test
    public void findByUsernameIgnoreArchived() throws Exception {

        User user = userRepository.findByUsernameIgnoreArchived("od");
        assertNull(user);

        user = newUser();

        fullClean();

        final User user1 = userRepository.findByUsernameIgnoreArchived(user.username());
        assertNotNull(user1);
        assertNull(user1.creator());
    }

    @Test
    public void deleteUserPrivileges() throws Exception {

        User user = newUser();

        fullClean();

        final int i = userRepository.deleteUserPrivileges(user);
        assertEquals(i, 0);

        UserPrivilege userPrivilege = new UserPrivilege(user, Privilege.DELETE_INSTANCE);
        userRepository.saveOrUpdate(userPrivilege);
        fullClean();

        final int i1 = userRepository.deleteUserPrivileges(user);
        assertEquals(i1, 1);

    }


    @Test
    public void testUserPrivilege() {
        final User user = newUser();

        UserPrivilege userPrivilege = new UserPrivilege(user, Privilege.CREATE_EDIT_INSTANCE);
        userRepository.saveOrUpdate(userPrivilege);

        fullClean();

        final UserPrivilege privilege = userRepository.findByGuid(userPrivilege.guid(), UserPrivilege.class);
        assertNotNull(privilege);
        assertNotNull(privilege.user());

    }

    private User newUser() {
        User user = new User("user", "123", "123", "ewo@honyee.cc");
        userRepository.saveOrUpdate(user);
        return user;
    }


    @Test
    public void findUserPrivileges() {
        final User user = newUser();

        UserPrivilege userPrivilege = new UserPrivilege(user, Privilege.CREATE_EDIT_INSTANCE);
        userRepository.saveOrUpdate(userPrivilege);

        fullClean();

        final List<Privilege> privileges = userRepository.findUserPrivileges(user);
        assertNotNull(privileges);
        assertEquals(privileges.size(), 1);

    }

    @Test
    public void findListUsers() {
        final User user = newUser();

        fullClean();

        Map<String, Object> map = new HashMap<>();
        map.put("user", SecurityUtils.currentUser());
        map.put("perPageSize", 20);
        map.put("startIndex", 0);
        map.put("username", user.username());

        final List<User> list = userRepository.findListUsers(map);
        assertTrue(list.size() == 1);

        final int i = userRepository.totalListUsers(map);
        assertEquals(i, 1);
    }


}