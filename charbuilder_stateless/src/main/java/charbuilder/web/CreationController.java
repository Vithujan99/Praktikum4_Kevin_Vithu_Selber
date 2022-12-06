package charbuilder.web;

import charbuilder.character.CharacterClass;
import charbuilder.character.CharacterInfo;
import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CreationController {


  @GetMapping("/")
  public String index() {
    return "start";
  }

  @GetMapping("/create/1")
  public String createNameForm() {
    return "name";
  }

  @PostMapping("/create/1")
  public String createStep1(RedirectAttributes redirectAttributes, String charactername) {
    if (charactername == null || charactername.isBlank()) return "name";
    CharacterInfo character = new CharacterInfo(charactername);
    redirectAttributes.addFlashAttribute("character", character);
    return "redirect:/create/2";
  }

  @GetMapping("/create/2")
  public String createClassForm(Model m) {
    return "class";
  }

  @PostMapping("/create/2")
  public String createStep3(String characterName,CharacterClass characterclass, RedirectAttributes redirectAttributes) {
    CharacterInfo character = new CharacterInfo(characterName);
    character.setCharacterClass(characterclass);
    Integer points = character.getPoints();
    redirectAttributes.addFlashAttribute("character", character);
    redirectAttributes.addFlashAttribute("points",points);
    return "redirect:/create/3";
  }

  @GetMapping("/create/3")
  public String createAttributeForm() {
    return "attributes";
  }

  @PostMapping("/create/3")
  public String createStep3(String characterName, CharacterClass characterclass, Integer points, Model m,
                            @RequestParam("Strength") int str,
                            @RequestParam("Dexterity") int dex,
                            @RequestParam("Constitution") int con,
                            @RequestParam("Intelligence") int intl,
                            @RequestParam("Wisdom") int wis,
                            @RequestParam("Charisma") int cha) {
    CharacterInfo character = new CharacterInfo(characterName);
    character.setCharacterClass(characterclass);
    character.setPoints(points);
    character.setAttribute("Strength",str);
    character.setAttribute("Dexterity", dex);
    character.setAttribute("Constitution", con);
    character.setAttribute("Intelligence", intl);
    character.setAttribute("Wisdom", wis);
    character.setAttribute("Charisma", cha);
    m.addAttribute("character", character);
    m.addAttribute("points",points);
    if(!character.isValid()){
      return "attributes";
    }
    System.out.println(character);
    // Character fertigstellen und validieren

    // Fehler: Formular wieder anzeigen, Werte müssen erhalten bleiben

    // Erfolg: Ausgabe der Spielerinformationen auf der Konsole und zurück zur Startseite

    return "redirect:/";
  }


}
