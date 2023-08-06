<?php

$basePath = 'docs/'; // Replace with the actual base directory path
$userCoursePath = $basePath . $userID . '/' . $courseID . '/';

// Create directories if they don't exist
if (!is_dir($userCoursePath)) {
    mkdir($userCoursePath, 0777, true);
}

$targetPath = $userCoursePath . basename($uploadedFile['name']);

    // Move the uploaded file to the target directory
if (move_uploaded_file($uploadedFile['tmp_name'], $targetPath)) {
    return $targetPath;
} else {
    return false; // Upload failed
}