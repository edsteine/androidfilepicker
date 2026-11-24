package com.edsteine.androidfilepicker.permissions.models

/**
 * Represents the possible states of a permission.
 *
 * This sealed class provides a type-safe way to manage the different outcomes of a
 * permission request.
 */
sealed class PermissionResult {
    /**
     * The permission was granted.
     */
    object Granted : PermissionResult()

    /**
     * The permission was denied, and the user might be shown a rationale.
     */
    object Requested : PermissionResult()

    /**
     * The permission was permanently denied, and the user needs to go to settings.
     */
    object RequiresSettings : PermissionResult()

    /**
     * The permission is not declared in the manifest.
     */
    object NotDeclared : PermissionResult()

    /**
     * An error occurred while trying to get the permission.
     */
    object Error : PermissionResult()

    /**
     * Represents a state where a specific permission is needed.
     *
     * @param permission The specific permission required.
     */
    data class NeedPermission(val permission: String) : PermissionResult()
}
