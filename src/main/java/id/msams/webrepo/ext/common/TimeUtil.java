package id.msams.webrepo.ext.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtil {

  @Data
  @Builder
  @AllArgsConstructor
  public static class TimeDomain {
    private long hours;
    private long minutes;
    private long seconds;
    private long millis;
    private long nanos;

    public TimeDomain(TimeDomain timeDomain) {
      this.hours = timeDomain.hours;
      this.minutes = timeDomain.minutes;
      this.seconds = timeDomain.seconds;
      this.millis = timeDomain.millis;
      this.nanos = timeDomain.nanos;
    }
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Convert {

    private final TimeDomain timeDomain;

    public Long toMillis() {
      // @formatter:off
			return
				(this.timeDomain.getHours() * 60 * 60 * 1000) +
				(this.timeDomain.getMinutes() * 60 * 1000) +
				(this.timeDomain.getSeconds() * 1000) +
				(this.timeDomain.getMillis())
			;
			// @formatter:on
    }

    public Long toSeconds() {
      return this.toMillis() / 1000L;
    }

    public Long toMinutes() {
      return this.toSeconds() / 60L;
    }

    public Long toHours() {
      return this.toMinutes() / 60L;
    }

  }

  public static Convert convert(TimeDomain timeDomain) {
    return new Convert(timeDomain);
  }

}
