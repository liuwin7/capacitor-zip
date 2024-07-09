import type { PluginListenerHandle } from "@capacitor/core";

/**
 * Progress object
 * 
 * @param progress progress 
 * @param current current file being processed
 * @param task task being processed
 */
export interface Progress {
  progress: number,
  current: string,
  task: string,
}

/**
 * Progress handler
 * 
 * @param progress progress object {@link Progress}
 */
export type ProgressHandler = (progress: Progress) => void;

export interface CapacitorZipPlugin {
  /**
   * unzip a zip file
   * @param options unzip options
   */
  unzip(options: UnzipOptions): Promise<UnzipResult>;

  /**
   * Listens for screen orientation changes.
   * @param eventName event name
   * @param listenerFunc listener function
   * 
   * @returns {Promise<PluginListenerHandle>} returns a promise which resolves 
   * if the listener added successfully
   */
  addListener(eventName: 'onProgress', listenerFunc: ProgressHandler)
    : Promise<PluginListenerHandle>;
}

export interface UnzipOptions {
  source: string;
  destination: string;
  password?: string;
}

export interface UnzipResult {
  message: string;
  uri?: string;
}

/*
 * @enum {string}
 */
export enum ZipErrorCodes {
  SUCCESS = 'SUCCESS',
  NO_SOURCE_SPECIFIED = 'NO_SOURCE_SPECIFIED',
  NO_DESTINATION_SPECIFIED = 'NO_DESTINATION_SPECIFIED',
  NO_PASSWORD_SPECIFIED = 'NO_PASSWORD_SPECIFIED',
  NO_PERMISSION = 'NO_PERMISSION',
  NO_FILE_EXISTS = 'NO_FILE_EXISTS',
  NO_FILE_VALID = 'NO_FILE_VALID',
  UNKNOWN_ERROR = 'UNKNOWN_ERROR',
}
