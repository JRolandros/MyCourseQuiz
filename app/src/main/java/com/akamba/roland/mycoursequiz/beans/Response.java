package com.akamba.roland.mycoursequiz.beans;

/**
 * Created by Roland on 26/12/2015.
 */
public class Response {
        private String CountryName;

        private String RegionName;

        private String MetroCode;

        private String IP;

        private String Latitude;

        private String Longitude;

        private String CountryCode;

        private String TimeZone;

        private String ZipCode;

        private String RegionCode;

        private String City;

        public String getCountryName ()
        {
            return CountryName;
        }

        public void setCountryName (String CountryName)
        {
            this.CountryName = CountryName;
        }

        public String getRegionName ()
        {
            return RegionName;
        }

        public void setRegionName (String RegionName)
        {
            this.RegionName = RegionName;
        }

        public String getMetroCode ()
        {
            return MetroCode;
        }

        public void setMetroCode (String MetroCode)
        {
            this.MetroCode = MetroCode;
        }

        public String getIP ()
        {
            return IP;
        }

        public void setIP (String IP)
        {
            this.IP = IP;
        }

        public String getLatitude ()
        {
            return Latitude;
        }

        public void setLatitude (String Latitude)
        {
            this.Latitude = Latitude;
        }

        public String getLongitude ()
        {
            return Longitude;
        }

        public void setLongitude (String Longitude)
        {
            this.Longitude = Longitude;
        }

        public String getCountryCode ()
        {
            return CountryCode;
        }

        public void setCountryCode (String CountryCode)
        {
            this.CountryCode = CountryCode;
        }

        public String getTimeZone ()
        {
            return TimeZone;
        }

        public void setTimeZone (String TimeZone)
        {
            this.TimeZone = TimeZone;
        }

        public String getZipCode ()
        {
            return ZipCode;
        }

        public void setZipCode (String ZipCode)
        {
            this.ZipCode = ZipCode;
        }

        public String getRegionCode ()
        {
            return RegionCode;
        }

        public void setRegionCode (String RegionCode)
        {
            this.RegionCode = RegionCode;
        }

        public String getCity ()
        {
            return City;
        }

        public void setCity (String City)
        {
            this.City = City;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [CountryName = "+CountryName+", RegionName = "+RegionName+", MetroCode = "+MetroCode+", IP = "+IP+", Latitude = "+Latitude+", Longitude = "+Longitude+", CountryCode = "+CountryCode+", TimeZone = "+TimeZone+", ZipCode = "+ZipCode+", RegionCode = "+RegionCode+", City = "+City+"]";
        }
}
