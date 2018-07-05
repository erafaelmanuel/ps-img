package io.ermdev.papershelf.psimg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class PsImgApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsImgApplication.class, args);
	}

	@Configuration
	public class ApplicationProperty {

		@Value("${ps.book-dir}")
		private String bookDirectory;

		@Value("${ps.cover-dir}")
		private String coverDirectory;

		@Value("${ps.server}")
		private String serverName;

		public String getBookDirectory() {
			return bookDirectory;
		}

		public void setBookDirectory(String bookDirectory) {
			this.bookDirectory = bookDirectory;
		}

		public String getCoverDirectory() {
			return coverDirectory;
		}

		public void setCoverDirectory(String coverDirectory) {
			this.coverDirectory = coverDirectory;
		}

		public String getServerName() {
			return serverName;
		}

		public void setServerName(String serverName) {
			this.serverName = serverName;
		}
	}
}
