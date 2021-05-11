package be.ipl.pae.utilisateur;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import be.ipl.pae.dal.DalServiceImplMock;

class UtilisateurUccImplTest1 {
  private UtilisateurUcc userUcc;
  private UtilisateurDto userDto;

  @BeforeEach
  void setUp() throws Exception {
    userUcc = new UtilisateurUccImpl(new UtilisateurDaoMock(), new DalServiceImplMock());
    userDto = new UtilisateurImplMock(null, null, null, null, null);
  }

  @Test
  void testSeConnecter() {

    userDto.setMdp("1234");
    userDto.setMail(null);
    assertNull(userUcc.seConnecter(userDto));
  }

  @Test
  void testSeConnecter2() {

    userDto.setMail("test@hotmail.be");
    assertNull(userUcc.seConnecter(userDto));
  }

  @Test
  void testSeConnecter3() {

    userDto.setMail("test@hotmail.be");
    userDto.setMdp("1234");
    assertNotNull(userUcc.seConnecter(userDto));
  }

  @Test
  void testSeConnecter4() {

    assertNull(userUcc.seConnecter(userDto));
  }

  @Test
  void testGetUser() {
    assertNull(userUcc.getUser(null));
  }

  @Test
  void testGetUser2() {
    assertNotNull(userUcc.getUser("test@hotmail.be"));
  }

  @Test
  void testInscription() {
    assertNull(userUcc.inscription(userDto));
  }

  @Test
  void testInscription2() {
    assertNull(userUcc.inscription(null));
  }

  // @Test
  // void testInscription3() {
  // assertNull(userUcc.inscription("test", "test", "test", null, "test"));
  // }
  //
  // @Test
  // void testInscription4() {
  // assertNull(userUcc.inscription("test", "test", null, "test", "test"));
  // }
  //
  // @Test
  // void testInscription5() {
  // assertNull(userUcc.inscription("test", null, "test", "test", "test"));
  // }
  //
  // @Test
  // void testInscription6() {
  // assertNull(userUcc.inscription(null, "test", "test", "test", "test"));
  // }


}
