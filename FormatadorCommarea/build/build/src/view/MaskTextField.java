package view;

import java.util.ArrayList;
import javafx.scene.control.TextField;

/**
 * Use:
	1) Create a instace of the class(Or put a , same as into the FXML)

	2) Use the function setMask(String mask) to create a mask

	3) With the characters above, set the mask [Ex: objectmask.setMask("XXXXXX")]

	Characters of the mask:

	* = Accept any character

	A = Only Alphanumerics

	N = Only Numbers

	L = Only Letters

	U = Only Uppercase Letters

	l = Only Lowercase Letters

	S = Any character, except Space

	P = Only Letters and Points

	M = Letters, Numbers and Points

	Examples above

	==================== EXAMPLES =====================

	String mask = "N!.N!"

	Accept only numbers separated by a point Ex: 1.1 , 111.111 , 123124.1 12312.00

	String mask = "NN.NN"

	Accept only two numbers before and after a point Ex: 11.11 , 33.33, 13.30

	String mask = "U"

	Accept any character and an Uppercase letter Ex: /L , L , [space]L

	String mask = "Ul!"

	Accept only an Uppercase letter, and one or a sequence of lowercase letters Ex: David, Augusto

	String mask = "M!@M!.P!"
 */
public class MaskTextField extends TextField {

    private String mask;
    private String informedMask;
    private boolean dependingOn;
    private ArrayList<String> patterns;

    public MaskTextField() {
        super();
        patterns = new ArrayList<String>();
    }

    public MaskTextField(String text) {
        super(text);
        patterns = new ArrayList<String>();
    }

    public MaskTextField(String text, String mask, boolean dependingOn) {
        super(text);
        patterns = new ArrayList<String>();
        informedMask = mask;
        this.dependingOn = dependingOn;
        setMask(mask);
    }


    @Override
    public void replaceText(int start, int end, String text) {


        String tempText = this.getText() + text;

        if(mask == null || mask.length() == 0){
            super.replaceText(start, end, text);
        }else if (tempText.matches(this.mask) || tempText.length() == 0) {        //text.length == 0 representa o delete ou backspace

            super.replaceText(start, end, text);

        } else {

            String tempP = "^";

            for (String patt : this.patterns) {

                tempP += patt;

                if (tempText.matches(tempP)) {

                    super.replaceText(start, end, text);
                    break;

                }

            }

        }

    }

    /**
     * @return the Regex Mask
     */
    public String getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(String mask) {

    	informedMask = mask;

        String tempMask = "^";

        for (int i = 0; i < mask.length(); ++i) {

            char temp = mask.charAt(i);
            String regex;
            int counter = 1;
            int step = 0;

            if (i < mask.length() - 1) {
                for (int j = i + 1; j < mask.length(); ++j) {
                    if (temp == mask.charAt(j)) {
                        ++counter;
                        step = j;
                    } else if (mask.charAt(j) == '!') {
                        counter = -1;
                        step = j;
                    } else {
                        break;
                    }
                }
            }
            switch (temp) {

                case '*':
                    regex = ".";
                    break;
                case 'S':
                    regex = "[^\\s]";
                    break;
                case 'P':
                    regex = "[A-z.]";
                    break;
                case 'M':
                    regex = "[0-z.]";
                    break;
                case 'A':
                    regex = "[0-z]";
                    break;
                case 'N':
                    regex = "[0-9]";
                    break;
                case 'L':
                    regex = "[A-z]";
                    break;
                case 'U':
                    regex = "[A-Z]";
                    break;
                case 'l':
                    regex = "[a-z]";
                    break;
                case '.':
                    regex = "\\.";
                    break;
                default:
                    regex = Character.toString(temp);
                    break;

            }

            if (counter != 1) {

                this.patterns.add(regex);

                if (counter == -1) {
                    regex += "+";
                    this.patterns.add(regex);
                } else {
                    String tempRegex = regex;
                    for (int k = 1; k < counter; ++k) {
                        regex += tempRegex;
                        this.patterns.add(tempRegex);
                    }
                }

                i = step;

            } else {
                this.patterns.add(regex);
            }

            tempMask += regex;

        }

        this.mask = tempMask + "$";

    }

	public String getInformedMask() {
		return informedMask;
	}

	public void setInformedMask(String informedMask) {
		this.informedMask = informedMask;
	}

	public boolean isDependingOn() {
		return dependingOn;
	}

	public void setDependingOn(boolean dependingOn) {
		this.dependingOn = dependingOn;
	}

}