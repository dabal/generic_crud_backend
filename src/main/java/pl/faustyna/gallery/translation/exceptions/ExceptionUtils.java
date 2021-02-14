package pl.faustyna.gallery.translation.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.faustyna.gallery.translation.json.JsonUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ExceptionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionUtils.class);

    public static void logException(final Exception e) {
        LOG.error(new StringBuilder().append(e.getMessage()).append(" - ").
                append(Arrays.stream(e.getStackTrace()).map(n -> n.toString()).collect(Collectors.joining("\n"))).toString());
    }

    public static String getExceptionJSON(final Exception e) {
        final Map<String, Object> map = new HashMap<>();
        map.put("error", e.getMessage());
        map.put("stack_trace", Arrays.stream(e.getStackTrace()).map(n -> n.toString()).collect(Collectors.joining("\\n")));
        return JsonUtils.convertToString(map);
    }
}
