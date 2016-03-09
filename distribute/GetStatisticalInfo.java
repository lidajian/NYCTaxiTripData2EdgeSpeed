import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map.Entry;

public class GetStatisticalInfo{

    private static class AnHour{
        public double sum;
        public double sum2;
        public double min;
        public double max;
        public long size;

        public AnHour(){
            sum = 0.0;
            sum2 = 0.0;
            min = 100;
            max = -1;
            size = 0;
        }

        public void put(double speed){
            sum += speed;
            sum2 += speed * speed;
            size++;
            if(min > speed) min = speed;
            if(max < speed) max = speed;
        }
    }

    private static class DayInWeek{
        public AnHour [] weekday = new AnHour[24];
        public AnHour [] weekend = new AnHour[24];

        public DayInWeek(){
            for(int i = 0; i < 24; i++){
                weekday[i] = new AnHour();
                weekend[i] = new AnHour();
            }
        }

        public void put(Calendar date, double speed){
            switch(date.get(Calendar.DAY_OF_WEEK)){
                case Calendar.SUNDAY:
                case Calendar.SATURDAY:
                    weekend[date.get(Calendar.HOUR_OF_DAY)].put(speed);
                    break;
                default:
                    weekday[date.get(Calendar.HOUR_OF_DAY)].put(speed);
            }
        }

        public String toString(){
            StringBuffer sbsum = new StringBuffer();
            StringBuffer sbsum2 = new StringBuffer();
            StringBuffer sbmin = new StringBuffer();
            StringBuffer sbmax = new StringBuffer();
            StringBuffer sbsize = new StringBuffer();
            AnHour temp;
            for(int i = 0; i < 24; i++){
                temp = weekday[i];
                if(temp.size == 0){
                    sbsum.append("0\t");
                    sbsum2.append("0\t");
                    sbmin.append("0\t");
                    sbmax.append("0\t");
                    sbsize.append("0\t");
                }else{
                    sbsum.append(temp.sum+"\t");
                    sbsum2.append(temp.sum2+"\t");
                    sbmin.append(temp.min+"\t");
                    sbmax.append(temp.max+"\t");
                    sbsize.append(temp.size+"\t");
                }
            }
            for(int i = 0; i < 24; i++){
                temp = weekend[i];
                if(temp.size == 0){
                    sbsum.append("0\t");
                    sbsum2.append("0\t");
                    sbmin.append("0\t");
                    sbmax.append("0\t");
                    sbsize.append("0\t");
                }else{
                    sbsum.append(temp.sum+"\t");
                    sbsum2.append(temp.sum2+"\t");
                    sbmin.append(temp.min+"\t");
                    sbmax.append(temp.max+"\t");
                    sbsize.append(temp.size+"\t");
                }
            }
            return sbsize.toString() + sbsum.toString() + sbsum2.toString() + sbmin.toString() + sbmax.toString();
        }
    }

    public static void main(String args[]){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            String edgeid = null;
            DayInWeek dayinweek = null;
            HashMap<String, DayInWeek> records = new HashMap<String, DayInWeek>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            while((line = br.readLine())!=null){
                try{
                    String [] parts = line.split("\t");
                    edgeid = parts[0];

                    if((dayinweek = records.get(edgeid)) == null){
                        dayinweek = new DayInWeek();
                        records.put(edgeid, dayinweek);
                    }

                    calendar.setTime(sdf.parse(parts[1]));
                    dayinweek.put(calendar, Double.parseDouble(parts[2]));
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
            Iterator iter = records.entrySet().iterator();
            while(iter.hasNext()){
                Entry entry = (Entry)iter.next();
                edgeid = (String) entry.getKey();
                dayinweek = (DayInWeek) entry.getValue();
                System.out.println(edgeid+"\t"+dayinweek.toString());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
