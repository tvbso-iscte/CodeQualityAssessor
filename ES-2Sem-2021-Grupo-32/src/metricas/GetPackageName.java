package metricas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

public class GetPackageName {
	static List<String> p = new ArrayList<>();
	static HashMap<Integer, String[]> packName = new HashMap<Integer, String[]>();
	static Integer count = 0;

	public static void listPackageName(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {

			try {
				CompilationUnit cu = StaticJavaParser.parse(file);
				if (cu.getPackageDeclaration().isPresent()) {
					String pack = cu.getPackageDeclaration().get().toString();
					String b = pack.trim().replace(";", "");
					// System.out.println(b);
					String[] p = path.split("/");
					String[] pName = { b.replace("package ", ""), p[p.length - 1] };
					count++;
					packName.put(count, pName);
					String[] v = packName.get(count);
					System.out.println(count + "-" + v[0] + "--" + v[1]);

				} else {
					
					String[] p = path.split("/");
					String x = "default package";
					String[] pName = { x, p[p.length - 1] };
					count++;
					packName.put(count, pName);
					String[] v = packName.get(count);
					System.out.println(count + "-" + v[0] + "--" + v[1]);

				}
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}).explore(projectDir);

	}

	public static void main(String[] args) {
		File projectDir = new File("C:\\\\Users\\\\Bombas\\\\Pictures\\\\ES_eclipse\\\\test");
		listPackageName(projectDir);
	}
}