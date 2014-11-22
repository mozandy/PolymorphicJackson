import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class App3 {
    private static final String PARITY_PLUS = "{\"type\": \"PARITY_PLUS\", \"foo\":{\"name\":\"parity\"}}";
    private static final String EXPLORE = "{\"type\": \"EXPLORE\", \"foo\":{\"other_name\":\"explore\"}}}";
    private static final String TEXTUAL = "{\"type\": \"TEXTUAL\", \"foo\":{\"text_name\":\"textual\"}}}";
    private static final String WAYFINDING = "{\"type\": \"WAYFINDING\", \"foo\":{\"directions\":\"turnLeft\"}}}";

    public static void main( String[] args ) {
        Manifest parityPlusFoo = null;
        Manifest exploreFoo = null;
        Manifest textualFoo = null;
        Manifest wayfindingFoo = null;

        parityPlusFoo = null;
        exploreFoo = null;
        try {
            System.out.println("Only Registration-based Deserialize");
            ObjectMapper ppMapper = new ObjectMapper();
            parityPlusFoo = ppMapper.readValue(PARITY_PLUS, Manifest.class);
	    ParityPlus ppPlusFoo = (ParityPlus)parityPlusFoo.getFoo();
            System.out.println("Foo Parity_Plus Name: " + ppPlusFoo.getName());
            ObjectMapper exploreMapper = new ObjectMapper();
            exploreFoo = exploreMapper.readValue(EXPLORE, Manifest.class);
	    Explore eFoo = (Explore)exploreFoo.getFoo();
            System.out.println("Foo Explore Other_Name: " + eFoo.getOtherName());
            ObjectMapper textMapper = new ObjectMapper();
            textualFoo = textMapper.readValue(TEXTUAL, Manifest.class);
	    Textual tFoo = (Textual)textualFoo.getFoo();
            System.out.println("Foo Textual Text_Name: " + tFoo.getTextName());
            ObjectMapper wayfindingMapper = new ObjectMapper();
            wayfindingFoo = wayfindingMapper.readValue(WAYFINDING, Manifest.class);
	    Wayfinding wayFoo = (Wayfinding)wayfindingFoo.getFoo();
            System.out.println("Foo Wayfinding Directions: " + wayFoo.getDirections());
        } catch (Exception e) {
            System.out.println("Error deserializaing registration-based only: " + e);
        }
        System.out.println("");

   } // end of main


    public static class Manifest {
	@JsonProperty("type")
	private String type;
    	@JsonTypeInfo(use=Id.NAME, include=As.EXTERNAL_PROPERTY, property="type")
   	@JsonSubTypes(value = { @JsonSubTypes.Type(value = ParityPlus.class, name = "PARITY_PLUS"),
    	@JsonSubTypes.Type(value = Explore.class, name = "EXPLORE"),
    	@JsonSubTypes.Type(value = Wayfinding.class, name = "WAYFINDING"),
    	@JsonSubTypes.Type(value = Textual.class, name = "TEXTUAL")
	 })
	private Foo foo;
	
        @JsonTypeId
	public String getType() {
		return type;
	}

	public Foo getFoo() {
		return foo;
	}

        @JsonTypeId
	public void setType(String type) {
		this.type = type;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}
    }

    public static abstract class Foo {
    }

    @JsonTypeName("EXPLORE")
    public static class Explore extends Foo {
	@JsonProperty("other_name")
	private String otherName;
	
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
        @Override public String toString() { return "Explore"; }
    }

    @JsonTypeName("PARITY_PLUS")
    public static class ParityPlus extends Foo {
	@JsonProperty("name")
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
        @Override public String toString() { return "ParityPlus"; }
    }

    @JsonTypeName("TEXTUAL")
    public static class Textual extends Foo {
	@JsonProperty("text_name")
	private String textName;
	
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
        @Override public String toString() { return "Textual"; }
    }

    @JsonTypeName("WAYFINDING")
    public static class Wayfinding extends Foo {
	@JsonProperty("directions")
	private String directions;
	
	public String getDirections() {
		return directions;
	}
	public void setDirections(String directions) {
		this.directions = directions;
	}
        @Override public String toString() { return "Wayfinding"; }
    }
}
