package commons;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CalculationUtils {
   
   public static String calculateTime(Timestamp writeDate) {
      
      int SEC = 60;
      int MIN = 60;
      int HOUR = 24;
      int DAY = 30;
      int MONTH = 12;
          
       long curTime = System.currentTimeMillis();
       long writeTime = writeDate.getTime();
       long diffTime = (curTime - writeTime) / 1000;


       String msg = null;

//       if(diffTime < SEC) {
//           // sec
//           msg = diffTime + "초전";
//       } else if ((diffTime /= SEC) < MIN) {
//           // min
//           msg = diffTime + "분전";
//       } else if ((diffTime /= MIN) < HOUR) {
//           // hour
//           msg = (diffTime ) + "시간전";
//       } else if ((diffTime /= HOUR) < DAY) {
//           // day
//           msg = (diffTime ) + "일전";
//       } else if ((diffTime /= DAY) < MONTH) {
//           // day
//           msg = (diffTime ) + "달전";
//       } else {
//           msg = (diffTime) + "년전";
//       }
       
       if(diffTime < SEC) {
           // sec
           msg = "방금 전";
       } else if ((diffTime / SEC) < 5) {
           // min
           msg = "5분 이내";
       }else if ((diffTime / SEC) < MIN) {
           // min
           msg = "1시간 이내";
       } else if ((diffTime / MIN) < HOUR) {
           // hour
           msg = "오늘";
       } else {
          SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
           msg = sdf.format(writeDate);
       }
       

   return msg;

   }

}
