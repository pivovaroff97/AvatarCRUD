package ru.pivovarov.AvatarCRUD.storage;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.pivovarov.AvatarCRUD.exceptionHandling.StorageException;
import ru.pivovarov.AvatarCRUD.exceptionHandling.StorageFileNotFoundException;
import ru.pivovarov.AvatarCRUD.service.StorageService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public String store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			File directory = new File(this.rootLocation.toAbsolutePath().toString());
			System.out.println("directory: " + directory);
			if (!directory.exists()) {
				init();
			}
			String fileKey = generateKey(file.getOriginalFilename());
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(fileKey))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
			return fileKey;
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public void deleteByFileKey(String fileKey) {
		try {
			if (fileKey != null) {
				Path file = Paths.get(this.rootLocation + "/" + fileKey);
				System.out.println(file);
				Files.delete(file);
			}
		} catch (IOException e) {
			throw new StorageFileNotFoundException(
					"Could not find file in storage: " + fileKey);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	private String generateKey(String name) {
		return DigestUtils.md5Hex(name + LocalDateTime.now().toString());
	}
}
