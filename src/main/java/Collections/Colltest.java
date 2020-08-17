package Collections;

import java.nio.ByteBuffer;

public class Colltest {
    public static void main(String[] args) {
        String s = "abcd";
        System.out.println(s.hashCode());
        ByteBuffer b = ByteBuffer.allocate(4);
    }
}

class Key{
    private Integer id;
    public Key(Integer id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Key key = (Key) o;
        return this.id.equals(key.id);
    }

    @Override
    public int hashCode() {
        int hashcode = id.hashCode();
        return hashcode;
    }
}
