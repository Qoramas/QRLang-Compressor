import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Statics {

	public static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 $:%*+-=./";
	public static String commentRegex = "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)";
	public static String cList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	public static String[][] matches = {
		{"\n",""},
		{"\r",""},
		{"\t",""},
		{"drawbox","DB"},
		{"write","WR"},
		{"draw","DR"},
		{"update","UP"},
		{"function", "FU"},
		{"array", "AR"},
		{"if","IF"},
		{"else","EL"},
		{"while","WH"},
		{"for", "FR"},
		{"keyup","KU"},
		{"keydown","KD"},
		{"keyleft","KL"},
		{"keyright","KR"},
		{"keyspace","KS"},
		{"keyenter","KE"},
		{"tiltup","TU"},
		{"tiltdown","TD"},
		{"tiltleft","TL"},
		{"tiltright","TR"},
		{">=","GE"},
		{"<=","LE"},
		{">","GT"},
		{"<","LT"},
		{"==","EE"},
		{"||","OR"},
		{"~", "TI"},
		{"!=", "NE"},
		{"!", "NO"},
		{"&&","AN"},
		{"*=","ME"},
		{"/=","DE"},
		{"*","MU"},
		{"/","DI"},
		{"%", "MO"},
		{"\\","ES"},
		{"'","QS"},
		{"(","LB"},
		{")","RB"},
		{"{","*"},
		{"}","."},
		{"[","LS"},
		{"]","RS"},
		{",","/"},
		{";",":"},
		{"=","%"},
		{" ", ""},
		{"ELIFLB", "EI"},
		{"IFLB", "IF"},
		{"DBLB", "DB"},
		{"DR*", "DR"},
		{"WRLB", "WR"},
		{"UP*", "UP"},
		{"ARLB", "AR"},
		{"FU*", "FU"}
	};

	public static String readFile(String path)  throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, StandardCharsets.UTF_8);
	}

}
