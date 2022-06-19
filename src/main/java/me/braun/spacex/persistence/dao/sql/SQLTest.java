package me.braun.spacex.persistence.dao.sql;

import me.braun.spacex.persistence.dao.DAOFactory;
import me.braun.spacex.persistence.dao.IRoleDAO;
import me.braun.spacex.persistence.entity.Account;
import me.braun.spacex.persistence.entity.Mission;
import me.braun.spacex.persistence.entity.enams.ERole;

import java.sql.Date;
import java.time.Instant;

public class SQLTest {
    private final SQLAccountDAO accountDAO = new SQLAccountDAO();
    private final SQLMissionDAO missionDAO = new SQLMissionDAO();
    private final SQLSpacecraftDAO spacecraft = new SQLSpacecraftDAO();
    private final IRoleDAO roleDAO = DAOFactory.getRoleDAO();
//    public void executeMission() {
//        missionDAO.findAll();
//        missionDAO.findById(60L);
//        missionDAO.delete(51L);
//        missionDAO.save(Mission.builder()
//                .title("Mission 6.9")
//                .description("flight to Moon")
//                .customer(6L)
//                .spaceCraft(5L)
//                .status((short) 4)
//                .serviceType((byte) 10)
//                .curator(8L)
//                .payloadWeigh(875D)
//                .date(new Date(java.util.Date.from(Instant.parse("2022-06-25T00:00:00.00Z")).getTime()))
//                .duration(34).build());
//
//        missionDAO.update(Mission.builder()
//                .id(57L)
//                .title("Mission 6.4")
//                .description(" flight to space ")
//                .customer(5L)
//                .spaceCraft(4L)
//                .status((short) 6)
//                .serviceType((byte) 2)
//                .curator(8L)
//                .payloadWeigh(280D)
//                .date(new Date(java.util.Date.from(Instant.parse("2022-07-25T00:00:00.00Z")).getTime()))
//                .duration(26).build());
//    }
//
//    public void executeAccount() {
//        accountDAO.findById(28L);
//        accountDAO.delete(24L);
//        accountDAO.findAll();
//        accountDAO.update(Account.builder()
//                .id(28L)
//                .firstName("Fred")
//                .lastName("Wesley")
//                .email("fredwesley@gmail.com")
//                .phone("+44343429754")
//                .role(roleDAO.findById(ERole.CUSTOMER.getId()).get())
//                .passwordHash("serfs").build());
//
//        accountDAO.save(Account.builder()
//                .firstName("George")
//                .lastName("Wesley")
//                .email("georgwesley@gmail.com")
//                .phone("+44865228744")
//                .role(roleDAO.findById(ERole.CUSTOMER.getId()).get())
//                .passwordHash("ugh7u6").build());
//    }
//
//    public void executeSpacecraft() {
//        spacecraft.delete(36L);
//        spacecraft.findById(35L);
//        spacecraft.findAll();

//        spacecraft.update(Spacecraft.builder()
//                .id(35L)
//                .craftTitle()
//                .capacity(59D)
//                .maxWeight(45)
//                .launchPrice(676000).build());
//
//        spacecraft.save(Spacecraft.builder()
//                .craftTitle((byte) 4)
//                .capacity(89D)
//                .maxWeight(640)
//                .launchPrice(1086000).build());
//   }
}
