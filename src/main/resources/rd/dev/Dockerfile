FROM 11.11.174.85:28086/cnpcrd/u14java:oraclejdk8
MAINTAINER CHENYB
COPY ./target/job.jar /opt/
CMD java -Dfile.encoding=utf-8 -jar -Duser.timezone=GMT+08 /opt/job.jar --spring.profiles.active=rd-dev