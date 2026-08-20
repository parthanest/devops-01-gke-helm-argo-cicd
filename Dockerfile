# =============================================================================
# OPTIONAL fallback Dockerfile.
# The pipeline uses Jib by default (no Docker daemon needed). This multistage
# build is provided only if you prefer a classic docker build.
# =============================================================================

# ---- Stage 1: build ----------------------------------------------------------
FROM gradle:8.9-jdk21 AS build
WORKDIR /workspace
COPY . .
RUN gradle bootJar --no-daemon

# ---- Stage 2: runtime (distroless, non-root) ---------------------------------
FROM gcr.io/distroless/java21-debian12:nonroot
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar /app/app.jar
USER nonroot
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
