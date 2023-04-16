package pojo;

import java.util.List;
import java.util.Map;

public class AbilityPojo {

    private Map<String , Object> map;
    private List<AbilityResultPojo> abilites;
    private boolean is_hidden;
    private int slot;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<AbilityResultPojo> getAbilites() {
        return abilites;
    }

    public void setAbilites(List<AbilityResultPojo> abilites) {
        this.abilites = abilites;
    }

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
