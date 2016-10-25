
public class Guess implements IGuess{

	@Override
	public String guess(String classname) {
		String r=classname;
		switch(classname){
		case "1":
			r+=" 0 2";
			break;
		case "0":
			r+=" 1 2";
			break;
		default:
			r+=" 0 1";
		}
		return r;
	}
}
