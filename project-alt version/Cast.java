package p2;

public class Cast implements Comparable<Cast> {

    private String name;
    private String id;
    // private String email;
    // private String phone;
    private String role;
    private String contacto;

    // Os MML non fan falta ¿?¿?¿?¿¿?¿?

    public Cast(String id, String Name, String contacto, String role) {
        this.name = name;
        // this.email = email;
        this.contacto = contacto;
        this.role = role;
        this.id = id;
    }

    public String getID() {
        return this.id;
    }

    public String getContacto() {
        return this.contacto;
    }

    public String getRole() {
        return this.role;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {

        String cadena = "Nombre='" + this.name + "'---Contacto='" + this.contacto + "'"
                + "'---Rol:'" + this.role + "'";
        return cadena;
    }

    public String toStringhtml() { // nombre id papel contacto

        String cadena = "Nombre='" + this.name + "'---ID='" + this.id + "'"
                + "'---Papel:'" + this.role + "'" + "'---Contacto:'" + this.contacto + "'";
        return cadena;
    }

    public int compareTo(Cast castExt) {
        int coincidencia = 0;
        switch (this.role) {
            case "Supporting":
                if (castExt.role == "Supporting") {
                    coincidencia = 1;
                }
                if (castExt.role == "Main") {
                    return 1;
                }
                if (castExt.role == "Extra") {
                    return 1;
                }
            case "Main":
                if (castExt.role == "Supporting") {
                    return -1;
                }
                if (castExt.role == "Main") {
                    coincidencia = 1;
                }
                if (castExt.role == "Extra") {
                    return 1;
                }
            case "Extra":
                if (castExt.role == "Supporting") {
                    return 1;
                }
                if (castExt.role == "Main") {
                    return 1;
                }
                if (castExt.role == "Extra") {
                    coincidencia = 1;
                }

        }
        if (coincidencia == 1) {
            if (this.id.compareTo(castExt.id) == 1) {
                return 1;
            }

            else if (this.id.compareTo(castExt.id) == -1) {
                return -1;
            }

            else {
                return this.id.compareTo(castExt.id);
            }
        }

        return 0;
    }
}