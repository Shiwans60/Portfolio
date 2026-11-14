
/*
 * Portfolio interactions for Shiwans Pal's portfolio.
 * Synchronized with the updated portfolio.html and style.css.
 *
 * Features:
 * - Compact floating navigation
 * - Mobile navigation menu
 * - Persistent dark/light theme
 * - Scroll reveal animations
 * - Active navigation highlighting
 * - Cursor spotlight effects
 * - Scroll-driven Education and Experience timelines
 * - Hero background and portrait parallax
 * - Expandable coursework support
 */

(() => {
  "use strict";

  // Progressive enhancement. Content remains visible if JS fails.
  document.documentElement.classList.add("js-enabled");

  const ready = (callback) => {
    if (document.readyState === "loading") {
      document.addEventListener("DOMContentLoaded", callback, {
        once: true
      });
    } else {
      callback();
    }
  };

  ready(() => {
    const doc = document;
    const win = window;
    const body = doc.body;

    const nav = doc.querySelector(".nav.nav-pill");
    const navMenu = nav?.querySelector(".nav-items");
    const menuButton = doc.querySelector(".menu-btn");
    const themeSwitch = doc.querySelector("#theme-switch");
    const yearElement = doc.querySelector(".footer-text span");

    const reduceMotionQuery =
        win.matchMedia("(prefers-reduced-motion: reduce)");

    const finePointerQuery =
        win.matchMedia("(hover: hover) and (pointer: fine)");

    const mobileQuery =
        win.matchMedia("(max-width: 850px)");

    let reduceMotion = reduceMotionQuery.matches;

    // ---------------------------------------------------------
    // Theme preference
    // ---------------------------------------------------------

    const readTheme = () => {
      try {
        return localStorage.getItem("theme") === "light"
            ? "light"
            : "dark";
      } catch (_error) {
        return "dark";
      }
    };

    const applyTheme = (theme, persist = false) => {
      const isLight = theme === "light";

      body.classList.toggle("light", isLight);
      body.classList.toggle("dark", !isLight);

      if (themeSwitch) {
        themeSwitch.checked = !isLight;
      }

      if (persist) {
        try {
          localStorage.setItem(
              "theme",
              isLight ? "light" : "dark"
          );
        } catch (_error) {
          // Theme remains active for this page view.
        }
      }
    };

    applyTheme(readTheme());

    themeSwitch?.addEventListener("change", () => {
      applyTheme(
          themeSwitch.checked ? "dark" : "light",
          true
      );
    });

    // Update copyright year.
    if (yearElement) {
      yearElement.textContent = String(
          new Date().getFullYear()
      );
    }

    // ---------------------------------------------------------
    // Compact navigation
    // ---------------------------------------------------------

    const closeNavigation = ({ restoreFocus = false } = {}) => {
      if (!nav || !menuButton) return;

      nav.classList.remove("is-open");

      menuButton.setAttribute("aria-expanded", "false");
      menuButton.textContent = "Menu";

      body.classList.remove("lock-screen");

      if (restoreFocus) {
        menuButton.focus();
      }
    };

    const openNavigation = () => {
      if (!nav || !menuButton) return;

      nav.classList.add("is-open");

      menuButton.setAttribute("aria-expanded", "true");
      menuButton.textContent = "Close";

      body.classList.add("lock-screen");

      if (mobileQuery.matches) {
        nav.querySelector("a")?.focus();
      }
    };

    if (nav && menuButton) {
      menuButton.setAttribute("aria-expanded", "false");

      menuButton.addEventListener("click", () => {
        if (nav.classList.contains("is-open")) {
          closeNavigation();
        } else {
          openNavigation();
        }
      });

      navMenu?.addEventListener("click", (event) => {
        if (
            event.target instanceof Element &&
            event.target.closest("a")
        ) {
          closeNavigation();
        }
      });

      doc.addEventListener("keydown", (event) => {
        if (
            event.key === "Escape" &&
            nav.classList.contains("is-open")
        ) {
          closeNavigation({ restoreFocus: true });
        }
      });

      const handleViewportChange = () => {
        closeNavigation();
      };

      if (typeof mobileQuery.addEventListener === "function") {
        mobileQuery.addEventListener(
            "change",
            handleViewportChange
        );
      } else if (typeof mobileQuery.addListener === "function") {
        mobileQuery.addListener(handleViewportChange);
      }
    }

    // ---------------------------------------------------------
    // Smooth in-page navigation
    // ---------------------------------------------------------

    const getHashTarget = (href) => {
      if (!href || href === "#") return null;

      try {
        return doc.getElementById(
            decodeURIComponent(href.slice(1))
        );
      } catch (_error) {
        return null;
      }
    };

    doc.querySelectorAll('a[href^="#"]').forEach((anchor) => {
      anchor.addEventListener("click", (event) => {
        const href = anchor.getAttribute("href");
        const target = getHashTarget(href);

        if (!target) return;

        event.preventDefault();

        closeNavigation();

        target.scrollIntoView({
          behavior: reduceMotion ? "auto" : "smooth",
          block: "start"
        });

        if (target.id && win.history?.replaceState) {
          win.history.replaceState(
              null,
              "",
              `#${target.id}`
          );
        }
      });
    });

    // ---------------------------------------------------------
    // Scroll reveal for non-timeline sections
    // ---------------------------------------------------------

    const revealElements = Array.from(
        doc.querySelectorAll(".reveal-on-scroll")
    );

    if (
        reduceMotion ||
        !("IntersectionObserver" in win)
    ) {
      revealElements.forEach((element) => {
        element.classList.add("is-revealed");
      });
    } else {
      const revealObserver = new IntersectionObserver(
          (entries, observer) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                entry.target.classList.add("is-revealed");
                observer.unobserve(entry.target);
              }
            });
          },
          {
            root: null,
            rootMargin: "0px 0px -8% 0px",
            threshold: 0.08
          }
      );

      revealElements.forEach((element) => {
        // Timeline sections use continuous scroll effects.
        if (element.classList.contains("tl-section")) {
          element.classList.add("is-revealed");
          return;
        }

        const rect = element.getBoundingClientRect();

        if (
            rect.top < win.innerHeight &&
            rect.bottom > 0
        ) {
          element.classList.add("is-revealed");
        } else {
          revealObserver.observe(element);
        }
      });
    }

    // ---------------------------------------------------------
    // Cursor spotlight effects
    // ---------------------------------------------------------

    const attachSpotlight = () => {
      if (!finePointerQuery.matches) return;

      doc.querySelectorAll(".interactive-card").forEach((card) => {
        if (card.dataset.spotlightReady === "true") {
          return;
        }

        card.dataset.spotlightReady = "true";

        let pointerFrame = 0;

        card.addEventListener("pointermove", (event) => {
          if (pointerFrame) {
            cancelAnimationFrame(pointerFrame);
          }

          pointerFrame = requestAnimationFrame(() => {
            const rect = card.getBoundingClientRect();

            card.style.setProperty(
                "--mouse-x",
                `${event.clientX - rect.left}px`
            );

            card.style.setProperty(
                "--mouse-y",
                `${event.clientY - rect.top}px`
            );
          });
        });

        card.addEventListener("pointerleave", () => {
          if (pointerFrame) {
            cancelAnimationFrame(pointerFrame);
          }

          card.style.setProperty("--mouse-x", "-500px");
          card.style.setProperty("--mouse-y", "-500px");
        });
      });
    };

    attachSpotlight();

    // ---------------------------------------------------------
    // Active navigation item follows the current section
    // ---------------------------------------------------------

    const navLinks = Array.from(
        doc.querySelectorAll(".nav-link[href^='#']")
    );

    const sectionsForNav = navLinks
        .map((link) => getHashTarget(
            link.getAttribute("href")
        ))
        .filter(Boolean);

    if (
        "IntersectionObserver" in win &&
        sectionsForNav.length
    ) {
      const navObserver = new IntersectionObserver(
          (entries) => {
            const visible = entries
                .filter((entry) => entry.isIntersecting)
                .sort(
                    (a, b) =>
                        Math.abs(a.boundingClientRect.top) -
                        Math.abs(b.boundingClientRect.top)
                );

            if (!visible.length) return;

            const activeId = `#${visible[0].target.id}`;

            navLinks.forEach((link) => {
              if (link.getAttribute("href") === activeId) {
                link.setAttribute(
                    "aria-current",
                    "location"
                );
              } else {
                link.removeAttribute("aria-current");
              }
            });
          },
          {
            root: null,
            rootMargin: "-25% 0px -65% 0px",
            threshold: 0
          }
      );

      sectionsForNav.forEach((section) => {
        navObserver.observe(section);
      });
    }

    // ---------------------------------------------------------
    // Continuous Education and Experience timeline effects
    // ---------------------------------------------------------

    const timelineWrappers = Array.from(
        doc.querySelectorAll(".tl-wrapper")
    );

    const timelines = timelineWrappers.map((wrapper) => ({
      wrapper,
      entries: Array.from(
          wrapper.querySelectorAll(".tl-entry")
      ),
      fill: wrapper.querySelector(".tl-line-fill")
    }));

    const clamp = (value, min, max) =>
        Math.min(max, Math.max(min, value));

    const hero = doc.querySelector(".header");

    let scrollFrame = 0;

    const updateScrollEffects = () => {
      scrollFrame = 0;

      const viewportFocusY = win.innerHeight * 0.48;

      const focusRange = Math.max(
          win.innerHeight * 0.72,
          520
      );

      timelines.forEach(({ wrapper, entries, fill }) => {
        const wrapperRect = wrapper.getBoundingClientRect();

        const lineStart = wrapperRect.top;
        const lineEnd = wrapperRect.bottom;

        const lineLength = Math.max(
            lineEnd - lineStart,
            1
        );

        // Calculate timeline progress.
        const progress = clamp(
            (viewportFocusY - lineStart) / lineLength,
            0,
            1
        );

        wrapper.style.setProperty(
            "--timeline-progress",
            progress.toFixed(4)
        );

        if (fill) {
          fill.style.transform = `scaleY(${progress})`;
        }

        let activeEntry = null;
        let strongestFocus = 0;

        entries.forEach((entry) => {
          const card = entry.querySelector(".tl-card");

          if (!card) return;

          const cardRect = card.getBoundingClientRect();

          const cardCenterY =
              cardRect.top + cardRect.height / 2;

          const normalizedDistance =
              Math.abs(cardCenterY - viewportFocusY) /
              focusRange;

          const focus = clamp(
              1 - normalizedDistance,
              0,
              1
          );

          // Smooth focus values.
          // Keep all cards readable while emphasizing the active card.
          const opacity = 0.82 + focus * 0.18;
          const blur = (1 - focus) * 0.7;
          const scale = 0.988 + focus * 0.012;
          const translateY = (1 - focus) * 4;

          card.style.setProperty(
              "--card-opacity",
              opacity.toFixed(3)
          );

          card.style.setProperty(
              "--card-blur",
              `${blur.toFixed(2)}px`
          );

          card.style.setProperty(
              "--card-scale",
              scale.toFixed(3)
          );

          card.style.setProperty(
              "--card-y",
              `${translateY.toFixed(2)}px`
          );

          if (focus > strongestFocus) {
            strongestFocus = focus;
            activeEntry = entry;
          }
        });

        entries.forEach((entry) => {
          entry.classList.toggle(
              "is-active",
              entry === activeEntry &&
              strongestFocus > 0.16
          );
        });
      });

      // Hero background and portrait parallax.
      if (hero && !reduceMotion) {
        const heroRect = hero.getBoundingClientRect();

        const progress = clamp(
            -heroRect.top / Math.max(heroRect.height, 1),
            0,
            1
        );

        hero.style.setProperty(
            "--hero-parallax",
            `${progress * 34}px`
        );

        hero.style.setProperty(
            "--portrait-parallax",
            `${progress * -12}px`
        );
      }
    };

    const scheduleScrollEffects = () => {
      if (reduceMotion || scrollFrame) return;

      scrollFrame = requestAnimationFrame(
          updateScrollEffects
      );
    };

    if (reduceMotion) {
      timelines.forEach(({ wrapper, entries, fill }) => {
        wrapper.style.setProperty(
            "--timeline-progress",
            "1"
        );

        if (fill) {
          fill.style.transform = "scaleY(1)";
        }

        entries.forEach((entry) => {
          entry.classList.remove("is-active");

          const card = entry.querySelector(".tl-card");

          if (!card) return;

          card.style.setProperty("--card-opacity", "1");
          card.style.setProperty("--card-blur", "0px");
          card.style.setProperty("--card-scale", "1");
          card.style.setProperty("--card-y", "0px");
        });
      });
    } else {
      win.addEventListener(
          "scroll",
          scheduleScrollEffects,
          { passive: true }
      );

      win.addEventListener(
          "resize",
          scheduleScrollEffects,
          { passive: true }
      );

      win.addEventListener(
          "load",
          scheduleScrollEffects,
          { once: true }
      );

      // Recalculate when coursework expands or collapses.
      doc.querySelectorAll(".tl-courses-toggle").forEach(
          (details) => {
            details.addEventListener(
                "toggle",
                scheduleScrollEffects
            );
          }
      );

      // Recalculate when timeline dimensions change.
      if (
          "ResizeObserver" in win &&
          timelineWrappers.length
      ) {
        const timelineResizeObserver =
            new ResizeObserver(scheduleScrollEffects);

        timelineWrappers.forEach((wrapper) => {
          timelineResizeObserver.observe(wrapper);
        });
      }

      scheduleScrollEffects();
    }

    // Reattach spotlight after images and layout have settled.
    win.addEventListener("load", attachSpotlight, {
      once: true
    });

    // Keep motion preference updated if the OS setting changes.
    const handleMotionPreference = (event) => {
      reduceMotion = event.matches;

      if (reduceMotion) {
        timelines.forEach(({ wrapper, entries, fill }) => {
          wrapper.style.setProperty(
              "--timeline-progress",
              "1"
          );

          if (fill) {
            fill.style.transform = "scaleY(1)";
          }

          entries.forEach((entry) => {
            const card = entry.querySelector(".tl-card");

            entry.classList.remove("is-active");

            if (!card) return;

            card.style.setProperty("--card-opacity", "1");
            card.style.setProperty("--card-blur", "0px");
            card.style.setProperty("--card-scale", "1");
            card.style.setProperty("--card-y", "0px");
          });
        });
      } else {
        scheduleScrollEffects();
      }
    };

    if (typeof reduceMotionQuery.addEventListener === "function") {
      reduceMotionQuery.addEventListener(
          "change",
          handleMotionPreference
      );
    } else if (typeof reduceMotionQuery.addListener === "function") {
      reduceMotionQuery.addListener(
          handleMotionPreference
      );
    }
  });
})();
