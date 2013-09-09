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
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.JarFileSystem;

/**
 *
 * @author pramoth
 */
public class BundleUtils {

    public static String getSymbolicName(File bundleJarFile) throws FileStateInvalidException {
        FileObject toFileObject = FileUtil.toFileObject(FileUtil.normalizeFile(bundleJarFile));
        JarFileSystem jarFileSystem = (JarFileSystem) FileUtil.getArchiveRoot(toFileObject).getFileSystem();
        Attributes mainAttributes = jarFileSystem.getManifest().getMainAttributes();
        return mainAttributes.getValue("Bundle-SymbolicName");
    }

    public static String getBundleVersion(File bundleJarFile) throws FileStateInvalidException, FileNotFoundException, IOException {
        FileObject toFileObject = FileUtil.toFileObject(FileUtil.normalizeFile(bundleJarFile));
        JarFileSystem jarFileSystem = (JarFileSystem) FileUtil.getArchiveRoot(toFileObject).getFileSystem();
        Attributes mainAttributes = jarFileSystem.getManifest().getMainAttributes();
        return mainAttributes.getValue("Bundle-Version");
    }
}
