/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.geniustree.virgo.maven;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Attributes;
import org.apache.maven.project.MavenProject;
import org.netbeans.modules.maven.api.PluginPropertyUtils;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.JarFileSystem;

/**
 *
 * @author pramoth
 */
public class BundleUtils {

    public static String getSymbolicName(MavenProject mavenProject) throws FileStateInvalidException {
        String symbolicName = PluginPropertyUtils.getPluginProperty(mavenProject, "org.apache.felix", "maven-bundle-plugin", "instructions", "Bundle-SymbolicName", null);
        if (symbolicName != null) {
            return symbolicName;
        } else {
            File finalFile = new File(mavenProject.getBasedir(), "target/" + mavenProject.getBuild().getFinalName() + ".jar");
            FileObject toFileObject = FileUtil.toFileObject(FileUtil.normalizeFile(finalFile));
            JarFileSystem jarFileSystem = (JarFileSystem) FileUtil.getArchiveRoot(toFileObject).getFileSystem();
            Attributes mainAttributes = jarFileSystem.getManifest().getMainAttributes();
            return mainAttributes.getValue("Bundle-SymbolicName");
        }
    }

    public static String getBundleVersion(File bundleJarFile) throws FileStateInvalidException, FileNotFoundException, IOException {
        FileObject toFileObject = FileUtil.toFileObject(FileUtil.normalizeFile(bundleJarFile));
        JarFileSystem jarFileSystem = (JarFileSystem) FileUtil.getArchiveRoot(toFileObject).getFileSystem();
        Attributes mainAttributes = jarFileSystem.getManifest().getMainAttributes();
        return mainAttributes.getValue("Bundle-Version");
    }
}
