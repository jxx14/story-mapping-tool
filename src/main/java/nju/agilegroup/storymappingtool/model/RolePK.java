package nju.agilegroup.storymappingtool.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RolePK implements Serializable{
    @Column(name = "map_id")
    private int mapId;

    @Column(name = "name")
    private String name;

    public RolePK(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = result & prime + mapId;
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(obj.getClass() != this.getClass()){
            return false;
        }
        RolePK other = (RolePK) obj;
        if(mapId == other.getMapId() && name.equals(((RolePK) obj).getName())){
            return true;
        }
        return false;
    }
}
