package ValidPalindrome;

public class Main {
    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
    }

    public static boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(Character.isLetterOrDigit(c)){
                sb.append(Character.toLowerCase(c));
            }
        }

        StringBuilder sb_reverse = new StringBuilder(sb.toString());
        sb_reverse.reverse();

        return sb.toString().contentEquals(sb_reverse);
    }
}
