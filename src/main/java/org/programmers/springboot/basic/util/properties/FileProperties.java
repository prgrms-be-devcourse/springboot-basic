package org.programmers.springboot.basic.util.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private final Map<String, PathProperties> names = new HashMap<>();
    private Resources resources;
    private String userDir;
    private String projDir;


    public Map<String, PathProperties> getNames() {
        return names;
    }
    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    public String getProjDir() {
        return projDir;
    }

    public void setProjDir(String projDir) {
        this.projDir = projDir;
    }

    public static class PathProperties {

        private String fileName;
        private String testFileName;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getTestFileName() {
            return testFileName;
        }

        public void setTestFileName(String testFileName) {
            this.testFileName = testFileName;
        }
    }

    public static class Resources {
        private String path;
        private String testPath;
        private String jar;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTestPath() {
            return testPath;
        }

        public void setTestPath(String testPath) {
            this.testPath = testPath;
        }

        public String getJar() {
            return jar;
        }

        public void setJar(String jar) {
            this.jar = jar;
        }
    }
}
